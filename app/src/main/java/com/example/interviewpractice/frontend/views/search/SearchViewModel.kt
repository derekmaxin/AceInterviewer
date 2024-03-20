package com.example.interviewpractice.frontend.views.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.views.mainview.MainViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Question



class SearchViewModel(private val model: MainModel): Subscriber {
    var search by mutableStateOf("")
    var searchResults by mutableStateOf<List<Question>>(emptyList())

    init {
        model.subscribe(this)
    }

    override fun update() {
        searchResults = model.searchResults
        Log.d(TAG,"updated search results on frontend")
    }
    companion object {
        private const val TAG = "SearchViewModel"
    }

}