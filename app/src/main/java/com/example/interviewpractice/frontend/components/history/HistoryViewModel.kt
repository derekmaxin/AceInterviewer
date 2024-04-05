package com.example.interviewpractice.frontend.components.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.LoaderMMViewModel
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.History
import java.util.Calendar
import java.util.Locale

class HistoryViewModel: LoaderMMViewModel() {

    //FRONTEND DATA
    private val calendar = Calendar.getInstance()

    var selectedMonth by mutableIntStateOf(calendar.get(Calendar.MONTH))
    var selectedYear by mutableStateOf(calendar.get(Calendar.YEAR).toString())

    //BACKEND DATA
    var historyChartData by mutableStateOf<List<History>>(emptyList())

    override fun update() {
        Log.d(TAG,"POTENTIAL HISTORY UPDATE: MODEL ${model.historyChartData}")
        Log.d(TAG,"FROM: VM ${historyChartData}")
//        historyHeatData = model.historyHeatData
        if (historyChartData != model.historyChartData) {
            historyChartData = model.historyChartData.toList()
            Log.d(TAG, "UPDATED HISTORY")
        }

    }
    companion object {
        private const val TAG = "HistoryViewModel"
    }
}