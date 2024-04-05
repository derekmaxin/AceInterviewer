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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.starselection.StarSelection
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag
import kotlin.math.absoluteValue
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
    val clarityVM: StarSelectionViewModel = viewModel(key="clarity")

    val understandingVM: StarSelectionViewModel = viewModel(key="understanding")

    val playBarViewModel: PlayBarViewModel = viewModel()


    LaunchedEffect(Unit){
        rvvm.addModel(mm)
        clarityVM.addModel(mm)
        understandingVM.addModel(mm)
        playBarViewModel.addModel(mm)
        c.fetchData(FetchType.TINDER)
    }
    DisposableEffect(Unit) {
        onDispose {
            rvvm.unsubscribe()
            clarityVM.unsubscribe()
            understandingVM.unsubscribe()
            playBarViewModel.unsubscribe()
        }
    }


    clarityVM.name = "Clarity"
    understandingVM.name = "Understanding"



//    val dummyQuestion = AnsweredQuestion(
//        textResponse = "How do you manage the memory of an object in C++?",
//        userID = c.getUser()
//    )

    val pagerState = rememberPagerState(pageCount = { 2 })

/*
    val density = LocalDensity.current

    val centerState by remember {
        mutableStateOf(AnchoredDraggableState(
            initialValue = 0f,
            anchors = DraggableAnchors {
                -1f at 1500f
                0f at 0f
                1f at -1500f
            },
            positionalThreshold = { distance: Float -> distance * 0.3f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween()
        ))
    }

    val rightState by remember {
        mutableStateOf(AnchoredDraggableState(
            initialValue = 1500f,
            anchors = DraggableAnchors {
                -1f at 1500f
                0f at 0f
                1f at -1500f
            },
            positionalThreshold = { distance: Float -> distance * 0.3f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween()
        ))
    }
    */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),

        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) { page ->
            Box(
                Modifier
                    .wrapContentHeight()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .padding(end = 16.dp)
            ) {
                if (page == 1) {
                    if ((rvvm.currentReviewData.isNotEmpty())) {
                        DummyQuestion(
                            qText = rvvm.currentReviewData.first().textResponse
                        )
                    }
                    else {
                        DummyQuestion(qText = "NO QUESTIONS TO REVIEW RIGHT NOW")
                    }
                } else {
                    if ((rvvm.currentReviewData.size > 1)) {
                        DummyQuestion(
                            qText = rvvm.currentReviewData[1].textResponse
                        )
                    }
                    else {
                        DummyQuestion(qText = "NO QUESTIONS TO REVIEW RIGHT NOW")
                    }
                }
            }
        }

        /*
        Box(
            modifier = Modifier
                .anchoredDraggable(centerState, Orientation.Horizontal)
                .offset {
                    IntOffset(
                        x = centerState
                            .requireOffset()
                            .roundToInt(), y = 0
                    )
                }
        ) {

         */

        PlayBar(playBarViewModel)

        StarSelection(understandingVM)
        StarSelection(clarityVM)

        SimpleOutlinedTextField(rvvm)
        if (rvvm.currentReviewData.isNotEmpty()) {
            Button(
                onClick = {

                    c.verifyReview(
                        rvvm.reviewText,
                        clarityVM.intScore,
                        understandingVM.intScore,
                        rvvm.currentReviewData.first()/*REPLACE WITH ACTUAL QUESTION ID*/
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}