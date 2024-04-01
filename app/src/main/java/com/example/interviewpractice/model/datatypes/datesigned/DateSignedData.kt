package com.example.interviewpractice.model.datatypes.datesigned

import java.util.Calendar
import java.util.Date

open class DateSignedData(private val date: Date) {

    fun relativeTime(): String {
        val currentTime = Calendar.getInstance()
        val signedTime = Calendar.getInstance().apply { time = date }

        val differenceInMillis = currentTime.timeInMillis - signedTime.timeInMillis

        val seconds = differenceInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> "$years year${if (years > 1) "s" else ""} ago"
            months > 0 -> "$months month${if (months > 1) "s" else ""} ago"
            weeks > 0 -> "$weeks week${if (weeks > 1) "s" else ""} ago"
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            else -> "$seconds second${if (seconds > 1) "s" else ""} ago"
        }
    }

}