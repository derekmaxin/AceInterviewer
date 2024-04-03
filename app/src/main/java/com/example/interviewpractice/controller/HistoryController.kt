package com.example.interviewpractice.controller

import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel

class HistoryController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun getUserHistoryDataByDate() {
        // TODO: Make a function in the model that grabs history data within a specific date range
    }

    companion object {
        private const val TAG = "HistoryController"
    }
}