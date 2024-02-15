package com.example.interviewpractice.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.viewmodel.Subscriber
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val model: Model): Subscriber {

    //View specific
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")

    // TODO: Include attributes that will be updated by the model
    // Recall that you should use mutableStateOf() in your definition.
    var loading: Boolean by mutableStateOf(false)
    var user: FirebaseUser? by mutableStateOf(null)


    init {
        model.subscribe(this)
    }

    override fun update() {
        Log.d(TAG, "Updated")
    }
    companion object {
        private const val TAG = "LoginViewModel"
    }
}