package com.example.interviewpractice.controller

import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import java.util.Date

class HistoryController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun getUserHistoryDataByDate(from: Date, to: Date) {
        handler("getUserHistoryDataByDate") {
            mm.getHistoryData(from,to,am.getUserID())
        }
        // TODO: Make a function in the model that grabs history data within a specific date range
    }

    companion object {
        private const val TAG = "HistoryController"
    }
}