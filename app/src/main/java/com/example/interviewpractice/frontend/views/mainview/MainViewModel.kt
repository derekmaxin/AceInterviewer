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
import com.google.firebase.firestore.auth.User

class MainViewModel( ): AMViewModel() {

    var loading: Int by mutableStateOf(1)
    var error: UIError? by mutableStateOf(null)
    var authLoading: Boolean by mutableStateOf(true)
    var user: FirebaseUser? by mutableStateOf(null)

    override fun update() {
        if (error != m.error) {
            error = m.error
            Log.d(TAG,"Updated error state: error -> ${error?.message}")
        }
        if (loading != m.loading) {
            loading = m.loading
        }
        if (authLoading != m.authLoading) {
            authLoading = m.authLoading
        }
    }
    companion object {
        private const val TAG = "MainViewModel"
    }

}