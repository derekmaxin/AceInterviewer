package com.example.interviewpractice.frontend.components.review

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class NotificationGroupViewModel(private val model: MainModel): Subscriber {

    var expanded = mutableStateOf(false)
    var notifications = mutableStateOf(listOf<String>(""))

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("NOTIFS VIEW MODEL", "Updated")
    }


}