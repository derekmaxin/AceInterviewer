package com.example.interviewpractice.helpers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.interviewpractice.types.UserException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun getCurrentDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.now().format(dateFormat)
}

fun verifyGenericString(str: String, field: String) {
    if (str.isEmpty()) {
        throw UserException("$field field is empty")
    }
}

