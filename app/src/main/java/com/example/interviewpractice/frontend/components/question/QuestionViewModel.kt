package com.example.interviewpractice.frontend.question

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber

class QuestionViewModel(private val model: AuthModel): Subscriber {

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