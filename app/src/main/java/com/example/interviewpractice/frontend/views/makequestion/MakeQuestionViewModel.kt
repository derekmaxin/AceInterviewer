package com.example.interviewpractice.frontend.views.makequestion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.MainModel

class MakeQuestionViewModel(model: MainModel) {
    var questionText by mutableStateOf("")
    var textAnswer by mutableStateOf(false)
    var voiceAnswer by mutableStateOf(false)

    var selectedTag1 by mutableStateOf(false)
    var selectedTag2 by mutableStateOf(false)
}


