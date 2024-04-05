package com.example.interviewpractice.frontend.views.answerquestion

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.views.mainview.Router
import com.example.interviewpractice.frontend.views.submitanswer.SubmitAnswer
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

@Composable
fun SimpleOutlinedTextField(vm: AnswerQuestionViewModel) {

    OutlinedTextField (
        modifier = Modifier
            .fillMaxWidth(),
        value = vm.textAnswer,
        onValueChange = { vm.textAnswer = it },
        label = { Text("Text Response") }
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AnswerScreen(mm: MainModel, qc: QuestionController, router: Router) {
    val vm: AnswerQuestionViewModel = viewModel()

    LaunchedEffect(Unit){
        vm.addModel(mm)
        qc.fetchData(FetchType.QUESTION)
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.unsubscribe()
        }
    }

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val question = vm.currentQuestion
            if (question == null || vm.localLoading) Loader()
            else {
                Question(question) {}
                if (question.hasText) {
                    SimpleOutlinedTextField(vm)
                }


                if (question.hasVoice) {
                    SubmitAnswer(qc = qc, vm=vm)
                }

            }
            Button(
                onClick = {
                    val question = vm.currentQuestion
                    val file = vm.audioFile
                    val context = vm.context
                    if (question!= null) {
                        qc.verifySubmitAnswer(answerText = vm.textAnswer,question.questionID, file, context ,hasVoice = question.hasVoice,hasText = question.hasText, tags=question.tags, questionText = question.questionText)
                        router.goToHome()
                    }

                },
                modifier = Modifier
                    .height(50.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Submit Answer",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }

        }

    }
}


