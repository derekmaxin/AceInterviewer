package com.example.interviewpractice.controller

import com.example.interviewpractice.model.MainModel

class StarSelectionController(val model: MainModel, val scoreIndex: Int) {

    fun updateStars(score: Int) {
        model.updateReviewScore(scoreIndex, score)
    }

}