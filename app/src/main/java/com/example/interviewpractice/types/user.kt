package com.example.interviewpractice.types

import java.util.Date

//Add more user fields here, like birthday, first/last name, photo url?, etc
data class User(
    val username: String = "",
    val email: String = "",
    val questionsAnswered: Int = 0,
    val fieldsOfInterest: List<Tag> = emptyList(),
    val birthday: Date? = null,
    val pfpURL: String = ""
)


