package com.example.interviewpractice.frontend.views.answerquestion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.Notification
import com.example.interviewpractice.types.Question

class AnswerQuestionViewModel(): MMViewModel() {

    //DATA FROM FRONTEND
    var textAnswer by mutableStateOf("")

    //DATA FROM BACKEND
    var currentQuestion by mutableStateOf<Question?>(null)


    override fun update() {

//        if (currentQuestion != model.currentQuestion) {
//            currentQuestion = model.currentQuestion
//            Log.d(TAG, "Updated notifications")
//        }
    }

    companion object {
        private const val TAG = "AnswerQuestionViewModel"
    }
}