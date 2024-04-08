package com.example.interviewpractice.frontend.views.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.LoaderMMViewModel
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag


class SearchViewModel(): LoaderMMViewModel() {
    var search by mutableStateOf("")

    var isFilterOptionsVisible = mutableStateOf(false)
    var currentlyFilteredBy = mutableStateListOf<Tag>()

    var isCompletedFilter by mutableStateOf(false)

    //DATA FROM BACKEND
    var searchResults by mutableStateOf<List<Pair<Question,Boolean>>>(emptyList())

    override fun update() {
        if (searchResults != model.searchResults) {
            searchResults = model.searchResults.toList()
            Log.d(TAG, "updated search results on frontend: ${searchResults.toString()}")
        }
    }
    companion object {
        private const val TAG = "SearchViewModel"
    }

}