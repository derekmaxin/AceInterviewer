package com.example.interviewpractice.frontend.views.profile.bestquestions

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.frontend.Subscriber

class BestQuestionsViewModel(): MMViewModel(),Subscriber {
    var userQuestions by mutableStateOf<List<Question>>(emptyList())

//    init {
//        model.subscribe(this)
//    }

    override fun update() {
//        userQuestions = model.userQuestions
//        Log.d(TAG,"updated list of questions user authored")
    }

    companion object {
        private const val TAG = "BestQuestions"
    }
}