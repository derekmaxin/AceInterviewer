package com.example.interviewpractice.frontend.components.starselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun StarSelection(viewModel: StarSelectionViewModel) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = viewModel.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            for (index in 1..5) {
                IconButton(
                    onClick = {
                        viewModel.intScore = index
                    }
                ) {
                    // Render filled star if score[index] is true, else outlined star
                    Icon(
                        if (index <= viewModel.intScore) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Star"
                    )
                }
            }
        }
    }
}