package com.example.interviewpractice.frontend.components.question

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun Question(q: Question, isAnswered: Boolean = false, boost: ()->Unit) {
    var modif = Modifier.padding()
    if (isAnswered) modif = Modifier.border(width = 3.dp, color = Color(0xFF4CAF50), shape = RoundedCornerShape(12.dp)) // Add a green border to the Card
    Card(
        onClick = {boost()},

            modifier = modif
            ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (isAnswered) {
                Text(
                    text = "COMPLETED",
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 4.dp, 0.dp),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 12.sp, // Adjust the font size to make it smaller
                        fontWeight = FontWeight.Bold, // Make the text bold
                        color = Color(0xFF4CAF50) // Change the text color to green
                    )
                )
            }
            Text(
                text = q.questionText,
                modifier = Modifier
                    .padding(4.dp,0.dp,4.dp,0.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (q.tags.isNotEmpty()) {
                val chunkedTags = q.tags.chunked(4)
                for (chunk in chunkedTags) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        for (tag in chunk) {
                            Tag(tag.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewQuestion(qText: String, tags: List<String> = emptyList()) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val questionText = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Question: ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append("$qText")
                }
            }
            Text(
                text = questionText,
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
fun DummyQuestion(qText: String, tags: List<String> = emptyList()) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
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
fun DummyQuestion3(qText: String, goToReview: ()->Unit, tags: List<String> = emptyList()) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
        ),
        onClick = {goToReview()}

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp).background(Color.DarkGray),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = qText,
                modifier = Modifier
                    .padding(4.dp, 0.dp, 4.dp, 0.dp),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
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
