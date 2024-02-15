package com.example.interviewpractice.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.viewmodel.auth.RegisterViewModel

class QuestionViewModel(private val model: Model): Subscriber {

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