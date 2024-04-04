package com.example.interviewpractice.frontend.components.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.History
import java.util.Calendar
import java.util.Locale

class HistoryViewModel: MMViewModel() {

    //FRONTEND DATA
    private val calendar = Calendar.getInstance()
    private val currentYear = calendar.get(Calendar.YEAR).toString()
    private val currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    var selectedMonth by mutableStateOf(currentMonth ?: "")
    var selectedYear by mutableStateOf(currentYear)
//    var historyData: List<ViewReviewScoresViewModel> = mutableListOf()
//    var historyHeatData: Map<Date,Int> = emptyMap()
    var historyChartData: List<History> = emptyList()

    //BACKEND DATA

    override fun update() {
//        historyHeatData = model.historyHeatData
        historyChartData = model.historyChartData
    }
}