package com.example.interviewpractice.frontend.components.playbar

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

enum class PlayState {
    PLAY, // If play, show pause icon
    PAUSE // If paused, show play icon
}

class PlayBarViewModel(): MMViewModel() {

    var playState = mutableStateOf(PlayState.PAUSE)
    var audioLengthSeconds = mutableIntStateOf(0)
    var audioFile = mutableStateOf("") // TODO
    var sliderPosition = mutableFloatStateOf(0f)
    fun switchState() {
        playState.value = if (playState.value == PlayState.PLAY) {
            PlayState.PAUSE
        } else {
            PlayState.PLAY
        }
    }

    override fun update() {
        Log.d("PLAYBAR VIEW MODEL", "Updated")
    }
}