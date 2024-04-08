package com.example.interviewpractice.frontend.views.home

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.views.mainview.Router
import com.example.interviewpractice.helpers.navToQuestion
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question

@Composable
//@Preview
fun HomeScreen(
    c: AuthController,
    mm: MainModel,
    qc: QuestionController,
    uc: UserController,
    r: Router
)
{
    val scrollState = rememberScrollState()

    val vm: HomeViewModel = viewModel()


    LaunchedEffect(Unit){
        vm.addModel(mm)
        // playBarVM.addModel(mm)
        c.fetchData(FetchType.RECOMMENDATION)
        uc.getQuestionsThisUserAnswered()
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.unsubscribe()
            // playBarVM.unsubscribe()
        }
    }

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
                .padding(bottom = 65.dp)
                .verticalScroll(scrollState),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Home",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            TextButton(onClick = { }) {
                Text("Question of the day",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (vm.algoResults == null || vm.localLoading) {
                    Loader()
                } else {
                    Question(q = vm.algoResults!!) {
                        navToQuestion(r.goToAnswerQuestion,vm.algoResults!!,qc)
                    }
                }



                //Question(questionVM)
            }
            Text(
                text = "My answered",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp)
            )


            for (questionAnswer in vm.questionsThisUserAnswered) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    //Log.d("question downloadUrl", "${questionAnswer.downloadUrl}")
                    val playBarVM: PlayBarViewModel = viewModel(key = questionAnswer.downloadUrl)
                    playBarVM.addModel(mm)
                    playBarVM.audioURL = questionAnswer.downloadUrl

                    QuestionAnswered(questionAnswer, playBarVM)
                }
            }

        }
    }
}

@Composable
fun QuestionAnswered(
    questionAnswer: AnsweredQuestion,
    playBarVM: PlayBarViewModel
) {
    Log.d("playbar downloadUrl", "${playBarVM.audioURL}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box() {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val questionText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Question: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("${questionAnswer.questionText}")
                        }
                    }
                    Text(
                        text = questionText,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "Your Answer",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(8.dp),
                    )
                    Text(
                        text = "${questionAnswer.textResponse}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PlayBar(playBarVM)
                }
            }
            }

        }
    }
}