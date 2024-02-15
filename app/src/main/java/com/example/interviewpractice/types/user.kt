package com.example.interviewpractice.types

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val _username: String, val _email: String) {
    val username: String? = _username
    val email: String? = _email
    //Add more user fields here, like birthday, first/last name, photo url?, etc
}