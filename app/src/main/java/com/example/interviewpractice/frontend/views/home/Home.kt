package com.example.interviewpractice.frontend.views.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

@Composable
//@Preview
fun HomeScreen(
    c: AuthController,
    mm: MainModel
)
{
    val scrollState = rememberScrollState()

    val vm: HomeViewModel = viewModel()
    vm.addModel(mm)
    val playBarVM: PlayBarViewModel = viewModel()
    playBarVM.addModel(mm)
    LaunchedEffect(Unit){
        c.fetchData(FetchType.RECOMMENDATION)
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
                    Question(q = vm.algoResults!!, {})
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

            for (i in 0..2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    QuestionAnswered(playBarVM)
                }
            }
        }
    }
}

@Composable
fun QuestionAnswered(playBarVM: PlayBarViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
    ) {
        Box() {// Needed to align notification number in top-right
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
                    Text(
                        text = "Question Text",
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
            Surface(
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Text(
                    text = "5",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}