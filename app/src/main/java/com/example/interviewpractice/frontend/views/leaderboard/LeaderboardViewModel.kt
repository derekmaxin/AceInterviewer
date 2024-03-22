package com.example.interviewpractice.frontend.views.leaderboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.components.historychart.HistoryChartViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.User

class LeaderboardViewModel(): MMViewModel() {

    //DATA FROM BACKEND
    var topUsers by mutableStateOf<List<User>>(emptyList())

    override fun update() {
        topUsers = model.leaderBoardStandings
        Log.d(TAG, "Updated")
    }
    companion object {
        private const val TAG = "ProfileViewModel"
    }
}