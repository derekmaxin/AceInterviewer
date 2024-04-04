package com.example.interviewpractice.frontend.components.viewreviewscores

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.QuestionViewModel

class ViewReviewScoresViewModel(): MMViewModel() {

    val questionText by mutableStateOf("")
    val reviewScores = mutableListOf<Pair<String, Int>>()

    val playBarViewModel = PlayBarViewModel()
    val questionViewModel = QuestionViewModel()

}