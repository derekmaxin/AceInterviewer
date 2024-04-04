package com.example.interviewpractice.frontend.views.review

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.starselection.StarSelection
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import kotlin.math.roundToInt

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

@OptIn(ExperimentalFoundationApi::class)
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
        userID = c.getUser()
    )


    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            initialValue = 0f,
            anchors = DraggableAnchors {
                -1f at 1500f
                0f at 0f
                1f at -1500f
            },
            positionalThreshold = { distance: Float -> distance * 0.3f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),

        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .anchoredDraggable(state, Orientation.Horizontal)
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(), y = 0
                    )
                }
        ) {
            DummyQuestion(
                qText = "How do you manage the memory of an object in C++?",
                tags = listOf("C++", "Programming"),
            )
        }

        PlayBar(playBarViewModel)

        StarSelection(understandingVM)
        StarSelection(clarityVM)

        SimpleOutlinedTextField(rvvm)

        Button(
            onClick = {c.verifyReview(rvvm.reviewText, clarityVM.intScore,understandingVM.intScore,dummyQuestion/*REPLACE WITH ACTUAL QUESTION ID*/)} ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}