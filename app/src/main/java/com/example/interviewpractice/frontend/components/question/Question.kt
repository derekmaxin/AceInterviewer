package com.example.interviewpractice.frontend.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Tag(text: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(12.dp, 6.dp, 12.dp, 6.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun Question(questionViewModel: QuestionViewModel?) {
    val viewModel by remember { mutableStateOf(questionViewModel) }

    Card(
        modifier = Modifier
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            viewModel?.questionText?.value?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .padding(4.dp,0.dp,4.dp,0.dp),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            if (viewModel?.showTags?.value == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (tag in viewModel?.tags?.value!!) {
                        Tag(tag)
                    }
                }
            }
        }


    }
}