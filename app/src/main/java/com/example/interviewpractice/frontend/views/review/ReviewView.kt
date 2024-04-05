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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.starselection.StarSelection
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.frontend.views.profile.leaderboard.LeaderboardViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag

@Composable
fun SimpleOutlinedTextField(rvvm: ReviewViewViewModel) {
//    var text by remember { mutableStateOf("Write your review...") }


    OutlinedTextField (
        modifier = Modifier
            .fillMaxWidth(),
        value = rvvm.reviewText,
        onValueChange = { rvvm.reviewText = it },
        label = { Text("Review Text") }
    )
}

@Composable
fun ReviewView(mm: MainModel, c: ReviewController){

    val rvvm: ReviewViewViewModel = viewModel()
    rvvm.addModel(mm)
    val clarityVM: StarSelectionViewModel = viewModel(key="clarity")
    clarityVM.addModel(mm)
    val understandingVM: StarSelectionViewModel = viewModel(key="understanding")
    understandingVM.addModel(mm)
    val playBarViewModel: PlayBarViewModel = viewModel()
    playBarViewModel.addModel(mm)

    clarityVM.name = "Clarity"
    understandingVM.name = "Understanding"

    val dummyQuestion = AnsweredQuestion(
        textResponse = "How do you manage the memory of an object in C++?",
        userID = c.getUser())

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
        
        PlayBar(playBarViewModel)

        /*
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

        }
         */

        StarSelection(understandingVM)
        StarSelection(clarityVM)

        SimpleOutlinedTextField(rvvm)

        Button( onClick = {c.verifyReview(rvvm.reviewText, clarityVM.intScore,understandingVM.intScore,dummyQuestion/*REPLACE WITH ACTUAL QUESTION ID*/)} ) {
            Text("Submit")
        }
    }
}