package com.example.interviewpractice.frontend.views.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.question.Question
import com.example.interviewpractice.frontend.question.QuestionViewModel
import com.example.interviewpractice.frontend.views.home.QuestionAnswered
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel

@Composable
//@Preview
fun SearchView(c: QuestionController, searchVM: SearchViewModel) {

    val vm by remember { mutableStateOf(searchVM) }
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = {c.dummyData() },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(vertical = 4.dp)
            ) {
                Text("Dummy Data Dump",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )

            }
            TextField(
                value = vm.password,
                onValueChange = {vm.password = it },
                label = { Text("Search Questions") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            Button(
                onClick = {c.search(vm.password) },
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(vertical = 4.dp)
            ) {
                Text("Search",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )

            }
        }
    }
}