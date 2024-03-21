package com.example.interviewpractice.frontend.components.notification

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

interface NotificationGroupViewModel: Subscriber {
    var expanded: MutableState<Boolean>
    var notifications: MutableState<List<String>>
    var defaultText: MutableState<String>
}

class ConcreteNotificationGroupViewModel(private val model: MainModel): NotificationGroupViewModel {

    override var expanded = mutableStateOf(false)
    override var notifications = mutableStateOf(listOf<String>(""))
    override var defaultText = mutableStateOf("")

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("NOTIFS VIEW MODEL", "Updated")
    }
}

class DummyNotificationGroupViewModel(notifications: List<String>, defaultText: String):
    NotificationGroupViewModel {

    override var expanded = mutableStateOf(false)
    override var notifications = mutableStateOf(notifications)
    override var defaultText = mutableStateOf(defaultText)
    override fun update() {
        Log.d("NOTIFS VIEW MODEL", "Updated")
    }
}
