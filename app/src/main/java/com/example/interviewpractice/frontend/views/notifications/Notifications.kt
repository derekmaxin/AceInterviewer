package com.example.interviewpractice.frontend.views.notifications

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.frontend.components.notification.NotificationGroup
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Notification

@Composable

fun Notifications(mm: MainModel, c: NotificationController, goToSeeReview: (Notification) -> Unit) {
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
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Your Notifications",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            if (vm.newReviewNotification.isNotEmpty()) {
                NotificationGroup("You have new reviews on your answers!", vm.newReviewNotification, goToSeeReview)
            }
            else {
                Text("No new notifications!",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ))
            }

//            for (group in viewModel.notifications.value) {
//
//            }
        }
    }
}
