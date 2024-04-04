package com.example.interviewpractice.frontend.components.historychart

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.MMViewModel
import java.time.LocalDate

class HistoryChartViewModel(): MMViewModel() {

    var calendar = mutableStateOf(emptyList<Pair<LocalDate, List<String>>>())

    /*
    The calendar contains a Pair<LocalDate, List<String>>.

    The List of strings in the second value of the pair is the list of questions IDs
    that were completed on each day.z
     */

    override fun update() {
        Log.d("HISTORY VIEW MODEL", "Updated")
    }
}