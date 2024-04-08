package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.helpers.getCurrentDate
import com.example.interviewpractice.helpers.verifyGenericString
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.User
import com.example.interviewpractice.types.UserException
import com.google.firebase.firestore.ListenerRegistration

class NotificationController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun listenForNotifications() {
        handler("listenForNotifications",true) {
            val currentUser = am.getUserID()
            mm.addNotificationListener(currentUser)


        }
    }

    fun removeNotification(reviewID: String) {
        handler("removeNotification",false) {
            val currentUser = am.getUserID()
            mm.removeNotification(reviewID, currentUser)
        }
    }

    companion object {
        private const val TAG = "NotificationController"
    }
}


