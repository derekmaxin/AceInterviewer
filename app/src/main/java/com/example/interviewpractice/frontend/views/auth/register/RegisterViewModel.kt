package com.example.interviewpractice.frontend.views.auth.register

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.AMViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class RegisterViewModel(): AMViewModel() {
    //View specific
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")
    var selectedOptions by mutableStateOf(setOf<Tag>())
    var selectedYear by mutableIntStateOf(YearMonth.now().year)
    var selectedMonth by mutableIntStateOf(YearMonth.now().monthValue)
    var selectedDay by mutableIntStateOf(LocalDate.now().dayOfMonth)


//    init {
//        m.subscribe(this)
//    }
//
//    override fun update() {
//        Log.d(TAG, "Updated")
//    }
    companion object {
        private const val TAG = "RegisterViewModel"
    }

}