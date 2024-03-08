package com.example.interviewpractice.frontend.views.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.notification.NotificationGroup

@Composable

fun Notifications(viewModel: NotificationsViewModel) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            for (group in viewModel.notifications.value) {
                NotificationGroup(group)
            }
        }
    }
}
