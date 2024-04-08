package com.example.interviewpractice.frontend.views.review

import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.question.DummyQuestion3
import com.example.interviewpractice.frontend.components.question.ReviewQuestion
import com.example.interviewpractice.frontend.components.starselection.StarSelection
import com.example.interviewpractice.frontend.components.starselection.StarSelectionViewModel
import com.example.interviewpractice.frontend.views.mainview.Router
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag
import kotlinx.coroutines.launch
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
fun ReviewView(mm: MainModel, c: ReviewController, router: Router){

    val rvvm: ReviewViewViewModel = viewModel()
    val clarityVM: StarSelectionViewModel = viewModel(key="clarity")
    val understandingVM: StarSelectionViewModel = viewModel(key="understanding")

    val btnscope = rememberCoroutineScope()

    val pagerState = rememberPagerState(pageCount = { 11 })
    LaunchedEffect(Unit){
        rvvm.addModel(mm)
        clarityVM.addModel(mm)
        understandingVM.addModel(mm)
        c.fetchData(FetchType.TINDER)
        clarityVM.name = "Clarity"
        understandingVM.name = "Completeness"
    }
    DisposableEffect(Unit) {
        onDispose {
            rvvm.unsubscribe()
            clarityVM.unsubscribe()
            understandingVM.unsubscribe()
        }
    }


    LaunchedEffect(pagerState.currentPage) {
        Log.d("REVIEWVIEW", "CURRENT PAGE: ${pagerState.currentPage}, ${pagerState.currentPage >= 10}")
        if (pagerState.currentPage >= 10 || pagerState.targetPage >= 10) {
            Log.d("REVIEWVIEW","INSIDE")
            // Refresh your content here, for example:
            // Navigate back to the first page
            pagerState.scrollToPage(0)
            mm.invalidate(FetchType.TINDER)
            c.fetchData(FetchType.TINDER)


        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),

        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                "Review Answers",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
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
                for (i in 0..10) {
                    if (i == page) {
                        if ((rvvm.currentReviewData.size > i)) {
                            ReviewQuestion(
                                qText = rvvm.currentReviewData[i].questionText
                            )
                        }
                        else if (i != 10) {
                            DummyQuestion(qText = "There are no more questions to review in your fields of interest. Check back later")
                        }
                        else {
                            DummyQuestion3(qText = "REFRESH", router.goToReview)
                        }
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
        if (rvvm.currentReviewData.size > pagerState.currentPage) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Given Answer:",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )

            )
            PlayBar(mm, rvvm.currentReviewData[pagerState.currentPage].downloadUrl,rvvm.currentReviewData[pagerState.currentPage].audioTime)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your Review:",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            )
            StarSelection(understandingVM)
            StarSelection(clarityVM)
            SimpleOutlinedTextField(rvvm)
        }

        if (rvvm.currentReviewData.size > pagerState.currentPage ) {
            if (rvvm.currentIDData[pagerState.currentPage] != "1") {
                Button(
                    onClick = {
                        c.verifyReview(
                            reviewText = rvvm.reviewText,
                            clarity = clarityVM.intScore,
                            understanding = understandingVM.intScore,
                            answeredQuestion = rvvm.currentReviewData[pagerState.currentPage],
                            answeredQuestionID = rvvm.currentIDData[pagerState.currentPage],

                            /*REPLACE WITH ACTUAL QUESTION ID*/
                        )
                        btnscope.launch {
                            val mut = rvvm.currentIDData.toMutableList()
                            mut[pagerState.currentPage] = "1"
                            rvvm.currentIDData = mut
                            pagerState.animateScrollToPage(pagerState.currentPage+1)

                        }




                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }
            else {
                Text(text = "You already answered this question!", style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                )
            }

        }
    }


}