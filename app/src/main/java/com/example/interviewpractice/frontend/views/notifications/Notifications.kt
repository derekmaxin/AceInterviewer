package com.example.interviewpractice.frontend.views.notifications

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.frontend.components.notification.ConcreteNotificationGroupViewModel
import com.example.interviewpractice.frontend.components.notification.NotificationGroup
import com.example.interviewpractice.frontend.components.notification.NotificationGroupViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.NotificationType

@Composable

fun Notifications(mm: MainModel, c: NotificationController) {
    val vm: NotificationsViewModel = viewModel()


    LaunchedEffect(Unit){
        vm.addModel(mm)
        c.fetchData(FetchType.NOTIFICATION)
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.unsubscribe()
        }
    }

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            if (vm.newReviewNotification.isNotEmpty()) {
                NotificationGroup("You have new reviews on your answers!", vm.newReviewNotification)
            }

//            for (group in viewModel.notifications.value) {
//
//            }
        }
    }
}
