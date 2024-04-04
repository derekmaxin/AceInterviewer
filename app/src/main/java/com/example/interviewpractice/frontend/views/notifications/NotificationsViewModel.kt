package com.example.interviewpractice.frontend.views.notifications

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.Notification

class NotificationsViewModel(): MMViewModel() {

//    var notifications = mutableStateOf(
//        listOf<NotificationGroupViewModel>(
//            DummyNotificationGroupViewModel(listOf<String>("Your question was denied!", "Your question got approved!", "Your question sucked!"), "This is a notification group"),
//            DummyNotificationGroupViewModel(listOf<String>("Your answer was reviewed!", "Your answer sucked!", "Keep trying!"), "This is another notification group")
//        )
//    )

    //DATA FROM BACKEND
    var newReviewNotification by mutableStateOf<List<Notification>>(emptyList())


    override fun update() {
        Log.d(TAG,model.newReviewNotifications.toString())

        if (newReviewNotification.size != model.newReviewNotifications.size) {
            newReviewNotification = model.newReviewNotifications
            Log.d(TAG, "Updated notifications")
        }
    }

    companion object {
        private const val TAG = "NotificationsViewModel"
    }
}