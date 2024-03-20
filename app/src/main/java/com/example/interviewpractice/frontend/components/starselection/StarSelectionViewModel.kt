package com.example.interviewpractice.frontend.components.starselection

import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class StarSelectionViewModel(private val model: MainModel, private val reviewScoreID: Int): Subscriber {

    var name = mutableStateOf("")
    private var intScore = 0
    var score = listOf(
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false))

    override fun update() {
        val newScore = model.reviewScores[reviewScoreID].second
        if (intScore != newScore) {
            intScore = newScore
            for ((index) in score.withIndex()) {
                score[index].value = index <= intScore
            }
        }
    }
}
