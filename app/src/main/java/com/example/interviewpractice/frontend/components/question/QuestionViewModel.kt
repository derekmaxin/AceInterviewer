package com.example.interviewpractice.frontend.components.question

import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.MMViewModel

class QuestionViewModel(): MMViewModel() {

    var showTags = mutableStateOf(false)
    var questionText = mutableStateOf("")
    var tags = mutableStateOf(emptyList<String>())

    init {
//        model.subscribe(this)
    }

    override fun update() {
//        Log.d("QUESTION VIEW MODEL", "Updated")
    }
}