package com.example.interviewpractice.frontend.views.mainview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.types.UIError
import com.google.firebase.auth.FirebaseUser

class MainViewModel(private val m: AuthModel): Subscriber {

    var loading: Boolean by mutableStateOf(false)
    var user: FirebaseUser? by mutableStateOf(null)
    var error: UIError? by mutableStateOf(null)


    init {
        m.subscribe(this)
    }

    override fun update() {
        if (error != m.error) {
            error = m.error
            Log.i(TAG,"Updated loading state: error -> $error")
        }
        if (user != m.user) {
            user = m.user
            Log.i(TAG,"Updated auth state: user -> $user")
        }
        if (loading != m.loading) {
            loading = m.loading
            Log.i(TAG,"Updated loading state: loading -> $loading")
        }




        Log.d(TAG, "Loading: $loading, user: ${user?.email}")
    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}