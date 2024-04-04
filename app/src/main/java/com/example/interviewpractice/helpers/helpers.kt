package com.example.interviewpractice.helpers

import com.example.interviewpractice.types.UserException
import java.util.Date


fun getCurrentDate(): Date {
    return Date()
}

fun verifyGenericString(str: String, field: String) {
    if (str.isEmpty()) {
        throw UserException("$field field is empty")
    }
}

