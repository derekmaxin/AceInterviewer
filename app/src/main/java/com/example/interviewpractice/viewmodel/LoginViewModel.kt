package com.example.interviewpractice.viewmodel

import android.util.Log
import com.example.interviewpractice.user_interface.ModelSubscriber
import com.example.interviewpractice.model.Model

class LoginViewModel(private val model: Model): ModelSubscriber {

    // TODO: Include attributes that will be updated by the model
    // Recall that you should use mutableStateOf() in your definition.

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("LOGIN VIEW MODEL", "Updated")
    }
}