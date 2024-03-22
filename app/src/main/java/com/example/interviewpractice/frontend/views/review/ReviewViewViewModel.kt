package com.example.interviewpractice.frontend.views.review

import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.model.MainModel

class ReviewViewViewModel(private val model: MainModel): Subscriber {

    val questionVM = mutableStateOf(QuestionViewModel(model))

    val reviewOneVM = mutableStateOf(StarSelectionViewModel(model))
    val reviewTwoVM = mutableStateOf(StarSelectionViewModel(model))

    val username = mutableStateOf("")

    override fun update() {
        TODO("Not yet implemented")
    }

}