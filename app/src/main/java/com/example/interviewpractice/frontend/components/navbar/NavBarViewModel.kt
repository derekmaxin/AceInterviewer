package com.example.interviewpractice.frontend.components.navbar

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel

class NavBarViewModel(): MMViewModel() {

    var notificationCount by mutableIntStateOf(0)

    override fun update() {
        if (notificationCount != model.notificationCount) {
            notificationCount = model.notificationCount
            Log.d(TAG, "New notification count: ${notificationCount}")
        }
    }

    companion object {
        private const val TAG = "NavBarViewModel"
    }
}