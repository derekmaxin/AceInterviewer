package com.example.interviewpractice.frontend.components.starselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun starSelection(text: String = "example") {
    var score by remember { mutableIntStateOf(1) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            val unfilled_stars = 5 - score

            for ( i in 1..score ) {
                IconButton(onClick = { score = i }) {
                    Icon(Icons.Filled.Star,
                        contentDescription = "Outlined Star",
                    )
                }

            }

            for ( i in score + 1..5) {
                IconButton(onClick = { score = i }) {
                    Icon(Icons.Filled.Star,
                        contentDescription = "Outlined Star",
                    )
                }
            }
        }
    }
}