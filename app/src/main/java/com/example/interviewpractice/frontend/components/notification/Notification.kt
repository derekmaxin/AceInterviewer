package com.example.interviewpractice.frontend.components.notification

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.review.NotificationViewModel

@Composable
fun Notification(viewModel: NotificationViewModel) {

    BadgedBox(
        badge = { Badge { Text(viewModel.notifications.value.size.toString()) } },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "default text",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                    ) {
                        IconButton(
                            onClick = { viewModel.expanded.value = !viewModel.expanded.value },
                            content = {
                                if (viewModel.expanded.value) { Icon(Icons.Filled.ExpandLess, contentDescription = null) }
                                else { Icon(Icons.Filled.ExpandMore, contentDescription = null) }

                            }
                        )
                    }
                }

                if (viewModel.expanded.value) {
                    for (notification in viewModel.notifications.value) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(6.dp),
                                text = notification,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}