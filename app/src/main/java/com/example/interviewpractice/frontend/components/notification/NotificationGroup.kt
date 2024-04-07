package com.example.interviewpractice.frontend.components.notification

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.types.Notification

@Composable
fun NotificationGroup(type: String, notifications: List<Notification>, goToSeeReview: (String) -> Unit) {
//    var expanded by MutableState<Boolean>(false)
    var expanded by remember {mutableStateOf(false)}
    BadgedBox(
        badge = { Badge { Text(notifications.size.toString()) } },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                //.fillMaxWidth()
                .animateContentSize(),
        ) {
            Column(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { expanded = !expanded },
                        content = {
                            if (expanded) { Icon(Icons.Default.ExpandLess, contentDescription = "collapse", modifier = Modifier.size(24.dp)) }
                            else { Icon(Icons.Default.Expand, contentDescription = "expand", modifier = Modifier.size(24.dp)) }

                        }
                    )
                }

                if (expanded) {
                    for (notification in notifications) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Log.d("ReviewIdInitial ","$notification.reviewID")
                                    goToSeeReview(notification.reviewID)
                                },
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Text(
                                modifier = Modifier.padding(6.dp),
                                text = notification.notificationText,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
