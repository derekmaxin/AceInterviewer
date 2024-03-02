package com.example.interviewpractice.frontend.views.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.frontend.NavBar
import com.example.interviewpractice.frontend.question.Question
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.model.AuthModel

@Composable
//@Preview
fun HomeScreen(controller: AuthController) {
    val c by remember { mutableStateOf(controller) }
    val model = AuthModel()
    val questionVM = QuestionViewModel(model)

    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

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
                Question(questionVM)
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
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    QuestionAnswered()
                }
            }
            Button(
                onClick = {c.verifyLogout() },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(vertical = 4.dp)
            ) {
                Text("Log out",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            NavBar()
        }

    }
}

@Composable
fun QuestionAnswered() {
    val model = AuthModel()
    val playBarVM = PlayBarViewModel(model)

    Card(
        modifier = Modifier.fillMaxWidth().height(125.dp)
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