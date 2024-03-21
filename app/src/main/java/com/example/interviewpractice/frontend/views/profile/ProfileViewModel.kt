package com.example.interviewpractice.frontend.views.profile

import android.util.Log
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel

class ProfileViewModel(private val model: MainModel): Subscriber {

    val badgeInfo = mutableMapOf<String, Int>()



    init {
        model.subscribe(this)

        //dummy
        badgeInfo["questionsAnswered"] = 100
    }

    override fun update() {
        Log.d("PROFILE VIEW MODEL", "Updated")
    }
}