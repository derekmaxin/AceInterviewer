package com.example.interviewpractice.frontend.views.auth.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.types.UIError

class RegisterViewModel(private val m: AuthModel) {
    //View specific
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")


//    init {
//        m.subscribe(this)
//    }
//
//    override fun update() {
//        Log.d(TAG, "Updated")
//    }
    companion object {
        private const val TAG = "RegisterViewModel"
    }

}