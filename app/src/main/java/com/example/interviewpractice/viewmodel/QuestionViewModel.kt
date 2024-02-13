package com.example.interviewpractice.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.user_interface.ModelSubscriber
import com.example.interviewpractice.model.Model

class QuestionViewModel(private val model: Model): ModelSubscriber {

    var showTags = mutableStateOf(false)
    var questionText = mutableStateOf("")
    var tags = mutableStateOf(emptyList<String>())

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("QUESTION VIEW MODEL", "Updated")
    }
}