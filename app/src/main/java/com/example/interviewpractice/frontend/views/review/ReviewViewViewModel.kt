package com.example.interviewpractice.frontend.views.review

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.Question

class ReviewViewViewModel(): MMViewModel() {

    var reviewText by mutableStateOf<String>("")
    var understandingScore by mutableIntStateOf(1)
    var clarityScore by mutableIntStateOf(1)
    var questions = mutableListOf<Question>()
    val questionsIndex = mutableIntStateOf(0)

    var cpage by mutableIntStateOf(0)

    var currentReviewData by mutableStateOf<List<AnsweredQuestion>>(emptyList())
    var currentIDData by mutableStateOf<List<String>>(emptyList())
//    val questionVM = mutableStateOf(QuestionViewModel(model))
//    val playBarViewModel = mutableStateOf(PlayBarViewModel(model))
//
//    val reviewOneVM = mutableStateOf(StarSelectionViewModel(model))
//    val reviewTwoVM = mutableStateOf(StarSelectionViewModel(model))


    override fun update() {
        Log.d(TAG,"Backend Review Data: ${model.currentReviewData}, ${currentReviewData}")
        if (currentReviewData != model.currentReviewData) {
            currentReviewData = model.currentReviewData.toList()
            currentIDData = model.currentIdData.toList()
            Log.d(TAG,"REVIEWVIEW CHANGED!: $currentReviewData")
        }
    }
    companion object {
        private const val TAG = "ReviewViewViewModel"
    }

}