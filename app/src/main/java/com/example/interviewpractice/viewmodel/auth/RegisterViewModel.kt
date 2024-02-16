package com.example.interviewpractice.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.viewmodel.Subscriber
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel(private val m: Model): Subscriber{
    //View specific
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")

    // TODO: Include attributes that will be updated by the model
    // Recall that you should use mutableStateOf() in your definition.
//    var loading: Boolean by mutableStateOf(false)
//    var user: FirebaseUser? by mutableStateOf(null)

    // TODO: implement errors
    //    var error by mutableStateOf("")
    var error: Exception? by mutableStateOf(null)


    init {
        m.subscribe(this)
    }

    override fun update() {
        error = m.error
        Log.d(TAG, "Updated")
    }
    companion object {
        private const val TAG = "RegisterViewModel"
    }

}