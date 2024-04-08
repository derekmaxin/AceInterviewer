package com.example.interviewpractice.frontend.components.playbar

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

enum class PlayState {
    PLAY, // If play, show pause icon
    PAUSE // If paused, show play icon
}

class PlayerService(private val url: String) {

    private var mediaPlayer: MediaPlayer? = null
    var currentPosition = 0

    var audioLength: Int = 0
        set(value) {
            if (value != field) {
                audioLengthListener(value)
                field = value
            }
        }

    var playState: PlayState = PlayState.PAUSE
        set(value) {
            currentPlayStateListener(value)
            field = value
        }

    private var currentPositionTimer: Timer? = null
    private var currentPositionListener: (Int) -> Unit = {}
    private var currentPlayStateListener: (PlayState) -> Unit = {}
    private var audioLengthListener: (Int) -> Unit = {}

    private fun startPositionListener() {
        currentPositionTimer = Timer()

        currentPositionTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if ( (mediaPlayer?.currentPosition ?: 0) != currentPosition) {
                    currentPosition = mediaPlayer?.currentPosition ?: 0
                    currentPositionListener(currentPosition)
                }
            }
        }, 0, 30)
    }

    private fun stopPositionListener() {
        Log.d("mps", "Stopped position timer")
        currentPositionTimer?.cancel()
        currentPositionTimer = null
    }

    fun setCurrentPositionListener(listener: (Int) -> Unit) { this.currentPositionListener = listener }
    fun setAudioLengthListener(listener: (Int) -> Unit) { this.audioLengthListener = listener }
    fun setPlayStateListener(listener: (PlayState) -> Unit) { this.currentPlayStateListener = listener }

    fun play() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
            }
        }
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(url)
                prepareAsync()
            } catch(e: IOException) {
                Log.e("mps", "No audio file submitted for this question")
            }
        }

        mediaPlayer?.setOnPreparedListener { mediaPlayer ->
            playState = PlayState.PLAY
            audioLength = mediaPlayer.duration
            mediaPlayer.seekTo(currentPosition.toInt())
            startPositionListener()
            mediaPlayer.start()

        }

        mediaPlayer?.setOnCompletionListener {
            playState = PlayState.PAUSE
            mediaPlayer?.pause()
            currentPosition = 0
            stopPositionListener()
        }
    }

    fun pause() {
        playState = PlayState.PAUSE
        mediaPlayer?.pause()
        stopPositionListener()
    }

    fun seekTo(time: Int) {
        currentPosition = time
    }

    fun release() {
        playState = PlayState.PAUSE
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0
    }

}


class PlayBarViewModel(): MMViewModel() {
    private var audioURLState: MutableState<String> = mutableStateOf("")
    var audioURL: String
        get() = audioURLState.value
        set(value) {
            audioURLState.value = value
            updatePlayerService()
        }

    var mps: PlayerService = PlayerService(audioURL)

    var audioLength by mutableIntStateOf(0)
    var currentPosition by mutableIntStateOf(0)
    var playState by mutableStateOf(mps.playState)

    private fun updatePlayerService() {
        mps = PlayerService(audioURL)

        mps.setAudioLengthListener {
            Log.d("mps", "audio length changed to $it")
            audioLength = it
        }
        mps.setCurrentPositionListener {
            //Log.d("mps", "current position changed to $it")
            currentPosition = it
        }
        mps.setPlayStateListener {
            Log.d("mps", "playstate changed")
            playState = it
        }
    }

    fun switchState() {
         if (playState == PlayState.PLAY) {
             Log.d("mps", "Pausing!")
             pause()
         } else {
             Log.d("mps", "Playing!")
             play()
         }
    }

    fun seek(time: Int) {
        pause()
        mps.seekTo(time)
    }
    private fun play() { mps.play() }
    private fun pause() { mps.pause() }

}