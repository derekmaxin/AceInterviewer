package com.example.interviewpractice.frontend.views.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.components.review.NotificationGroupViewModel
import com.example.interviewpractice.model.MainModel

class NotificationsViewModel(private val model: MainModel): Subscriber {

    var notifications = mutableStateOf(emptyList<NotificationGroupViewModel>())

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("NOTIFSCREEN VIEW MODEL", "Updated")
    }
}