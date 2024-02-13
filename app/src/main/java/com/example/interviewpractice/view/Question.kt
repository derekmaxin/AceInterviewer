package com.example.interviewpractice.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.viewmodel.QuestionViewModel

@Composable
// @Preview
fun Question(questionViewModel: QuestionViewModel) {
    val viewModel by remember { mutableStateOf(questionViewModel) }

    Card() {
        Text(
            text = viewModel.questionText,
            modifier = Modifier
                .padding(12.dp),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.bodySmall,
        )
        if (showTags) {
            Card() {

            }
        }

    }
}