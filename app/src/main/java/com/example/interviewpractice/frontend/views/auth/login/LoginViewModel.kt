package com.example.interviewpractice.frontend.views.auth.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.AuthModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.AMViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(): AMViewModel() {

    //View specific
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")


//    init {
//        model.subscribe(this)
//    }
//
//    override fun update() {
//        //Nothing to update right now
//        Log.d(TAG, "Updated")
//    }
    companion object {
        private const val TAG = "LoginViewModel"
    }
}