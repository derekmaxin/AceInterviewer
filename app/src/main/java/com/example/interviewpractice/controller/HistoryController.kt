package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import java.util.Date

class HistoryController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun getUserHistoryDataByDate(from: Date, to: Date) {
        handler("getUserHistoryDataByDate") {
            Log.d(TAG, "$from to $to")

            mm.invalidate(FetchType.HISTORY)
            mm.getHistoryData(from,to,am.getUserID())
            Log.d(TAG,"${mm.check(FetchType.HISTORY)}")
        }
        // TODO: Make a function in the model that grabs history data within a specific date range
    }

    companion object {
        private const val TAG = "HistoryController"
    }
}