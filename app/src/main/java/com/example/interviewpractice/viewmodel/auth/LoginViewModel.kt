package com.example.interviewpractice.viewmodel.auth

import android.util.Log
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.viewmodel.Subscriber

class LoginViewModel(private val model: Model): Subscriber {

    // TODO: Include attributes that will be updated by the model
    // Recall that you should use mutableStateOf() in your definition.

    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d("LOGIN VIEW MODEL", "Updated")
    }
}