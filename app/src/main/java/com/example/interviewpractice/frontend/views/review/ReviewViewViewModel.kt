package com.example.interviewpractice.frontend.views.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Tag
import java.time.LocalDate
import java.time.YearMonth

class ReviewViewViewModel(): MMViewModel() {

    var reviewText by mutableStateOf<String>("")
    var understandingScore by mutableIntStateOf(1)
    var clarityScore by mutableIntStateOf(1)

//    val questionVM = mutableStateOf(QuestionViewModel(model))
//    val playBarViewModel = mutableStateOf(PlayBarViewModel(model))
//
//    val reviewOneVM = mutableStateOf(StarSelectionViewModel(model))
//    val reviewTwoVM = mutableStateOf(StarSelectionViewModel(model))


    override fun update() {
        //TODO("Not yet implemented")
    }

}