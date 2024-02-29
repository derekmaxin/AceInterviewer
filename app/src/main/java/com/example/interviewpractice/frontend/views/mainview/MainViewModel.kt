package com.example.interviewpractice.frontend.views.mainview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.types.UIError
import com.google.firebase.auth.FirebaseUser

class MainViewModel(private val m: Model): Subscriber {

    var loading: Boolean by mutableStateOf(false)
    var user: FirebaseUser? by mutableStateOf(null)
    var error: UIError? by mutableStateOf(null)


    init {
        m.systemDataSubscribe(this)
    }

    override fun update() {
        error = m.error
        user = m.user
        loading = m.loading
        Log.i(TAG,"Update auth state")
        Log.d(TAG, "Loading: $loading, user: ${user?.email}")
    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}