package com.example.interviewpractice.frontend.views.profile.bestquestions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.question.DummyQuestion2

@Composable
fun BestQuestionsView(vm: BestQuestionsViewModel) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            for (question in vm.userQuestions) {
                DummyQuestion2(qText = question.questionText , tags = question.tags)
                Spacer(modifier = Modifier.padding(4.dp))
            }

        }
    }
}