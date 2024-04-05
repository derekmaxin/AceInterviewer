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

enum class PlayState {
    PLAY, // If play, show pause icon
    PAUSE // If paused, show play icon
}

class PlayerService(private val url: String) {
    var mediaPlayer: MediaPlayer? = null
    var currentPosition by mutableFloatStateOf(0F)
    var audioLength by mutableIntStateOf(0)
    var isSetup = false

    fun setup() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
            }
        }
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
        }

        mediaPlayer?.setOnPreparedListener { mediaPlayer ->

            Log.d("PlayerService","$audioLength")
        }

        play()
    }

    fun play() {
        mediaPlayer?.setOnPreparedListener { mediaPlayer ->
            //audioLength = mediaPlayer.duration
            Log.d("PlayerService","$audioLength")
            mediaPlayer.seekTo(currentPosition.toInt())
            mediaPlayer.start()
        }
    }

    fun pause() {
        mediaPlayer?.let {
            currentPosition = it.currentPosition.toFloat()
            it.pause()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        currentPosition = 0F
    }

    fun release() {
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0F
    }

}


class PlayBarViewModel(): MMViewModel() {
    var audioURLState: MutableState<String> = mutableStateOf("")
    var audioURL: String
        get() = audioURLState.value
        set(value) {
            audioURLState.value = value
            updatePlayerService()
        }

    var mps: PlayerService = PlayerService(audioURL)

    private fun updatePlayerService() {
        mps = PlayerService(audioURL)
    }

    var playState by mutableStateOf(PlayState.PAUSE)
    var audioLengthSeconds by mutableIntStateOf(mps.audioLength)


    fun switchState() {
         if (playState == PlayState.PLAY) {
             Log.d("mps", "${mps.audioLength}")
             Log.d("mps", "pausing!")
             playState = PlayState.PAUSE
             pause()
         } else {
             Log.d("mps", "playing!")
             playState = PlayState.PLAY
            play()
         }
    }

    private fun play() {
        if (mps.isSetup) {
            mps.play()
        } else {
            mps.setup()
        }
    }

    private fun pause() {
        mps.pause()
    }

    private fun goTo(time: Float) {

    }
}