package com.example.interviewpractice.frontend.views.search.makequestion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Tag

class MakeQuestionViewModel(): MMViewModel() {
    var questionText by mutableStateOf("")

    var hasText by mutableStateOf(false)
    var hasVoice by mutableStateOf(false)

    var biologyTag by mutableStateOf(false)
    var englishTag by mutableStateOf(false)
    var chemistryTag by mutableStateOf(false)
    var artTag by mutableStateOf(false)
    var computerScienceTag by mutableStateOf(false)
    var mathTag by mutableStateOf(false)
    var financeTag by mutableStateOf(false)
    var physicsTag by mutableStateOf(false)
    var businessTag by mutableStateOf(false)

    fun makeTagList(): MutableList<Tag> {
        val tagList = mutableListOf<Tag>()
        if (biologyTag) {
            tagList.add(Tag.BIOLOGY)
        }
        if (englishTag) {
            tagList.add(Tag.ENGLISH)
        }
        if (chemistryTag) {
            tagList.add(Tag.CHEMISTRY)
        }
        if (artTag) {
            tagList.add(Tag.ART)
        }
        if (computerScienceTag) {
            tagList.add(Tag.CS)
        }
        if (mathTag) {
            tagList.add(Tag.MATH)
        }
        if (financeTag) {
            tagList.add(Tag.FINANCE)
        }
        if (physicsTag) {
            tagList.add(Tag.PHYSICS)
        }
        if (businessTag) {
            tagList.add(Tag.BUSINESS)
        }
        return tagList
    }

    fun clearFields() {
        questionText = ""

        hasText = false
        hasVoice = false

        biologyTag = false
        englishTag = false
        chemistryTag = false
        artTag = false
        computerScienceTag = false
        mathTag = false
        financeTag = false
        physicsTag = false
        businessTag = false
    }
}


