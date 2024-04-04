package com.example.interviewpractice.frontend.components.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReviewScoreComponent(text: String, score: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )

        Row() {
            for ( i in 1..5) {
                if (i <= score) {
                    Icon(Icons.Filled.Star, contentDescription = "Star")
                } else {
                    Icon(Icons.Outlined.StarOutline, contentDescription = "Outlined Star")
                }

            }
        }
    }


}