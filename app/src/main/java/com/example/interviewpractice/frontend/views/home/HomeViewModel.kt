package com.example.interviewpractice.frontend.views.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.LoaderMMViewModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.views.profile.ProfileViewModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.User

class HomeViewModel: LoaderMMViewModel(), Subscriber {
    //DATA FROM BACKEND
    var algoResults by mutableStateOf<Question?>(null)
    var questionsThisUserAnswered = mutableListOf<AnsweredQuestion>()

    var user by mutableStateOf<User?>(null)

    override fun update() {
        if (algoResults != model.homePageRecommendations) {
            algoResults = model.homePageRecommendations
            Log.d(TAG, "updated search results on frontend: ${algoResults.toString()}")
        }
        if (questionsThisUserAnswered != model.userAnswered) {
            questionsThisUserAnswered = model.userAnswered
            Log.d(TAG, "updated answered questions on frontend: $questionsThisUserAnswered")
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}