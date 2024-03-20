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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.interviewpractice.controller.StarSelectionController

@Composable
fun StarSelection(viewModel: StarSelectionViewModel, controller: StarSelectionController) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = viewModel.name.value,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            for ((index, star) in viewModel.score.withIndex()) {
                IconButton(
                    onClick = {
                        controller.updateStars(index)
                    }
                ) {
                    // Render filled star if score[index] is true, else outlined star
                    Icon(
                        if (star.value) Icons.Filled.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Star"
                    )
                }
            }
        }
    }
}