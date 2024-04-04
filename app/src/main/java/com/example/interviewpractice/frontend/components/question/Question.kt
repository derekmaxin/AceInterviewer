package com.example.interviewpractice.frontend.components.question

import androidx.compose.foundation.gestures.anchoredDraggable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag

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
                .padding(8.dp, 4.dp, 8.dp, 4.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
@Composable
fun Question(q: Question, boost: ()->Unit) {

    Card(onClick = {boost()} ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = q.questionText,
                modifier = Modifier
                    .padding(4.dp,0.dp,4.dp,0.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (q.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (tag in q.tags) {
                        Tag(tag.toString())
                    }
                }
            }
        }
    }
}



@Composable
fun DummyQuestion(qText: String, tags: List<String>) {
    Card {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = qText,
                modifier = Modifier
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (tag in tags) {
                        Tag(tag)
                    }
                }
            }
        }
    }
}

@Composable
fun DummyQuestion2(qText: String, tags: List<Tag>) {
    Card() {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = qText,
                modifier = Modifier
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (tag in tags) {
                        Tag(tag.toString())
                    }
                }
            }
        }
    }
}
