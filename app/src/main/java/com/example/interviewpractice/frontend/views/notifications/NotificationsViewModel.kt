package com.example.interviewpractice.frontend.views.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.components.notification.NotificationGroup
import com.example.interviewpractice.frontend.components.notification.DummyNotificationGroupViewModel
import com.example.interviewpractice.frontend.components.notification.NotificationGroupViewModel
import com.example.interviewpractice.model.MainModel

class NotificationsViewModel(private val model: MainModel): Subscriber {

    var notifications = mutableStateOf(
        listOf<NotificationGroupViewModel>(
            DummyNotificationGroupViewModel(listOf<String>("Your question was denied!", "Your question got approved!", "Your question sucked!"), "This is a notification group"),
            DummyNotificationGroupViewModel(listOf<String>("Your answer was reviewed!", "Your answer sucked!", "Keep trying!"), "This is another notification group")
        )
    )

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("NOTIFSCREEN VIEW MODEL", "Updated")
    }
}