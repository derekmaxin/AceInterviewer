package com.example.interviewpractice.frontend.views.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.LoaderMMViewModel
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.Question

class HomeViewModel: LoaderMMViewModel() {
        //DATA FROM BACKEND
        var algoResults by mutableStateOf<Question?>(null)

        override fun update() {
            if (algoResults != model.homePageRecommendations) {
                algoResults = model.homePageRecommendations
                Log.d(TAG, "updated search results on frontend: ${algoResults.toString()}")
            }
        }
        companion object {
            private const val TAG = "HomeViewModel"
        }
}