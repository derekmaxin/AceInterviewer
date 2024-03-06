package com.example.interviewpractice.frontend.components.question

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class QuestionViewModel(private val model: MainModel): Subscriber {

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