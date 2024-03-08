package com.example.interviewpractice.frontend.views.makequestion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.MainModel

class MakeQuestionViewModel(model: MainModel) {
    var questionText by mutableStateOf("")
    var textAnswer by mutableStateOf(false)
    var voiceAnswer by mutableStateOf(false)

    var biologyTag by mutableStateOf(false)
    var englishTag by mutableStateOf(false)
    var chemistryTag by mutableStateOf(false)
    var artTag by mutableStateOf(false)
    var computerScienceTag by mutableStateOf(false)
    var mathTag by mutableStateOf(false)
    var financeTag by mutableStateOf(false)
    var physicsTag by mutableStateOf(false)
    var businessTag by mutableStateOf(false)
}


