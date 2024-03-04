package com.example.interviewpractice.frontend.views.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.MainModel

class SearchViewModel(model: MainModel) {
    var password by mutableStateOf("")
}