package com.example.interviewpractice.frontend.views.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.Question

class ReviewViewViewModel(): MMViewModel() {

    var reviewText by mutableStateOf<String>("")
    var understandingScore by mutableIntStateOf(1)
    var clarityScore by mutableIntStateOf(1)
    var questions = mutableListOf<Question>()
    val questionsIndex = mutableIntStateOf(0)

//    val questionVM = mutableStateOf(QuestionViewModel(model))
//    val playBarViewModel = mutableStateOf(PlayBarViewModel(model))
//
//    val reviewOneVM = mutableStateOf(StarSelectionViewModel(model))
//    val reviewTwoVM = mutableStateOf(StarSelectionViewModel(model))


    override fun update() {
        //TODO("Not yet implemented")
    }

}