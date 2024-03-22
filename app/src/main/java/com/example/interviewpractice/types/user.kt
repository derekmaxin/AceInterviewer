package com.example.interviewpractice.types

import com.google.firebase.firestore.IgnoreExtraProperties

//Add more user fields here, like birthday, first/last name, photo url?, etc
data class User(
    val username: String = "",
    val email: String = "",
    val questionsAnswered: Int = 0)

