package com.example.interviewpractice.model

import com.example.interviewpractice.viewmodel.Subscriber
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Model: Presenter() {
    private val subscribers = mutableListOf<Subscriber>()
    private var auth = Firebase.auth
    init{

    }
    fun checkAuth() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            notifySubscribers()
        }
    }
}