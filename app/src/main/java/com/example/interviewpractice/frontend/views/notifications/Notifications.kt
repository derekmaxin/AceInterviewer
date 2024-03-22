package com.example.interviewpractice.frontend.views.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.frontend.components.notification.ConcreteNotificationGroupViewModel
import com.example.interviewpractice.frontend.components.notification.NotificationGroup
import com.example.interviewpractice.model.MainModel

@Composable

fun Notifications(mm: MainModel) {
    val viewModel: NotificationsViewModel = viewModel()
    viewModel.addModel(mm)
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
