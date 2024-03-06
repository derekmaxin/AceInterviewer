package com.example.interviewpractice.frontend.components.review

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class NotificationViewModel(private val model: MainModel): Subscriber {

    var expanded = mutableStateOf(false)

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("REVIEW VIEW MODEL", "Updated")
    }


}