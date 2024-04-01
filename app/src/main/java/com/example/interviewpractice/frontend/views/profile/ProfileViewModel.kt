package com.example.interviewpractice.frontend.views.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.frontend.components.historychart.HistoryChartViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel(): MMViewModel(){

//    val hcVM = HistoryChartViewModel(model)

    val badgeInfo = mutableMapOf<String, Int>()

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    //DATA FROM BACKEND
    var user by mutableStateOf<User?>(null)

    init {

        //dummy
        badgeInfo["questionsAnswered"] = 100
    }

    override fun update() {
        if (user != model.user) {
            user = model.user
            Log.d(TAG, "New profile data: ${user.toString()}")
        }


    }
    companion object {
        private const val TAG = "ProfileViewModel"
    }
}