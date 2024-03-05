package com.example.interviewpractice.frontend.components.review

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class ReviewViewModel(private val model: MainModel): Subscriber {

    var reviewText = mutableStateOf("")
    var reviewScores = mutableStateOf(listOf<Pair<String,Int>>())

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("REVIEW VIEW MODEL", "Updated")
    }


}