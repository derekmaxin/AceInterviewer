package com.example.interviewpractice.frontend.views.mainview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.AMViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.UIError
import com.google.firebase.auth.FirebaseUser

class MainViewModel( ): AMViewModel() {

    var loading: Boolean by mutableStateOf(false)
    var user: FirebaseUser? by mutableStateOf(null)
    var error: UIError? by mutableStateOf(null)

    override fun update() {
        if (error != m.error) {
            error = m.error
            Log.d(TAG,"Updated error state: error -> ${error?.message}")
        }
        if (user != m.user) {
            user = m.user
            Log.d(TAG,"Updated auth state: user -> $user")
        }
        if (loading != m.loading) {
            loading = m.loading
            Log.d(TAG,"Updated loading state: loading -> $loading")
        }
    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}