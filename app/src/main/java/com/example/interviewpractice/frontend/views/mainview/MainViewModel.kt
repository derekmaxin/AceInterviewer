package com.example.interviewpractice.frontend.views.mainview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.frontend.Subscriber
import com.google.firebase.auth.FirebaseUser

class MainViewModel(private val m: Model): Subscriber {

    var loading: Boolean by mutableStateOf(false)
    var user: FirebaseUser? by mutableStateOf(null)
    var error: Exception? by mutableStateOf(null)


    init {
        m.subscribe(this)
        m.systemDataSubscribe(this)
    }

    override fun update() {
        error = m.error
        user = m.user
        loading = m.loading
        Log.d(TAG, "Updated")
    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}