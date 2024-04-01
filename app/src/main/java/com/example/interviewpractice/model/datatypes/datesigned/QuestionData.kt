package com.example.interviewpractice.model.datatypes.datesigned

import java.util.Date

data class QuestionData(
    private val date: Date,
    private val questionText: String,
    private val tags: List<String>,
    )
    : DateSignedData(date) {

}