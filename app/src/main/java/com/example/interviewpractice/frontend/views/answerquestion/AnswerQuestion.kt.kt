package com.example.interviewpractice.frontend.views.answerquestion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.question.DummyQuestion

@Composable
fun SimpleOutlinedTextField() {
    var text by remember { mutableStateOf("Write your response...") }

    OutlinedTextField (
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Text Response") }
    )
}

@Composable
@Preview
fun AnswerScreen() {

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            DummyQuestion(
                qText =     "What is the difference between a reference, and a pointer?",
                tags =      listOf<String>("C++", "Programming")
            )

            SimpleOutlinedTextField()
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                horizontalArrangement = Arrangement.Center
            ) {
                Button( onClick = { TODO("Start Recording") }) {
                    Icon(Icons.Filled.RadioButtonUnchecked, contentDescription = "Play")
                }
            }
        }
    }
}


