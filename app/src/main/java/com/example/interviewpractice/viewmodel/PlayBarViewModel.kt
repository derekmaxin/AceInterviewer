package com.example.interviewpractice.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.Model

enum class PlayState {
    PLAY, // If play, show pause icon
    PAUSE // If paused, show play icon
}

class PlayBarViewModel(private val model: Model): Subscriber {

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

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("PLAYBAR VIEW MODEL", "Updated")
    }
}