package com.example.interviewpractice.frontend.views.profile.leaderboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.User

class LeaderboardViewModel(): MMViewModel() {

    //DATA FROM BACKEND
    var topUsers by mutableStateOf<List<User>>(emptyList())

    override fun update() {
        if (topUsers != model.leaderBoardStandings) {
            topUsers = model.leaderBoardStandings
            Log.d(TAG, "Updated leaderboard results")
        }

    }
    companion object {
        private const val TAG = "ProfileViewModel"
    }
}