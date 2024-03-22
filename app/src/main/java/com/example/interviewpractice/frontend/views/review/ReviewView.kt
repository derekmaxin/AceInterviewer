package com.example.interviewpractice.frontend.views.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.components.starselection.StarSelection

@Composable
fun SimpleOutlinedTextField() {
    var text by remember { mutableStateOf("Write your review...") }

    OutlinedTextField (
        modifier = Modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = { text = it },
        label = { Text("Review Text") }
    )
}

@Composable
fun ReviewView(rvvm: ReviewViewViewModel, /* */){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),

        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        DummyQuestion(
            qText = "How do you manage the memory of an object in C++?",
            tags = listOf("C++", "Programming")
        )
        
        PlayBar(rvvm.playBarViewModel.value)

        /*
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

        }
         */

        rvvm.reviewOneVM.value.name = "Understanding"
        rvvm.reviewTwoVM.value.name = "Clarity"

        StarSelection(rvvm.reviewOneVM.value)
        StarSelection(rvvm.reviewTwoVM.value)

        SimpleOutlinedTextField()

        Button( onClick = {/* controller.sendReviewToDatabase(rvvm.reviewOneVM.value.intScore.value) */} ) {
            Text("Submit")
        }

        //StarSelection("Sample")
        //TODO: Add Marcus' tiered feedback review component
    }
}