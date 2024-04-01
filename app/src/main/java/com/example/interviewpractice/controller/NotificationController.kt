package com.example.interviewpractice.controller

import com.example.interviewpractice.helpers.getCurrentDate
import com.example.interviewpractice.helpers.verifyGenericString
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.ReviewScore
import com.example.interviewpractice.types.UserException

class NotificationController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun listenForNotifications() {
        handler("listenForNotifications",true) {
            val currentUser = am.getUserID()
            mm.addNotificationListener(currentUser)
        }
    }
    companion object {
        private const val TAG = "NotificationController"
    }
}


