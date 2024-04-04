package com.example.interviewpractice.frontend.components.starselection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel

class StarSelectionViewModel(): MMViewModel() {

    //Frontend data

    var name by mutableStateOf("")
    var intScore by mutableIntStateOf(1)
    var score = listOf(
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false),
        mutableStateOf(false))

    override fun update() {

    }
}
