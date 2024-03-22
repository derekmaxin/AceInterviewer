package com.example.interviewpractice.frontend.views.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.views.leaderboard.LeaderboardViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Tag

@Composable
//@Preview
fun SearchView(c: QuestionController, mm: MainModel, goToMakeQuestion: () -> Unit) {
    val vm: SearchViewModel = viewModel()
    vm.addModel(mm)
    LaunchedEffect(Unit){
        c.fetchData(FetchType.SEARCH)
        Log.d("SEARCHVIEW","RERENDERED SEARCH!!!")
    }
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {/*c.dummyData()*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Dummy Data Dump",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                ) {
                    IconButton(
                        onClick = {
                            if (vm.filtering)c.search(vm.search, setOf(Tag.MATH))
                            else c.search(vm.search)

                        },
                        modifier = Modifier
                            .width(45.dp)
                            .height(70.dp)
                            .padding(vertical = 8.dp),
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                    )
                }
                OutlinedTextField(
                    value = vm.search,
                    onValueChange = { vm.search = it },
                    modifier = Modifier
                        .height(85.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    placeholder = { Text("Search Questions") },
                    textStyle = TextStyle(color = Color.Black),
                    singleLine = true,
                    shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 16.dp, bottomEnd = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { c.search(vm.search) }
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {vm.filtering = !vm.filtering },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Filter",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Completed",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    )
                }
            }
            Text(text = "Not here?")
            Button(
                onClick = { goToMakeQuestion() },
                modifier = Modifier
                    .height(50.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Add new",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
            // TODO: Render clickable questions components on search, collab with ryan,
            // also remove "dummy data dump" button
            if (vm.localLoading) Loader()
            for (question in vm.searchResults) {
                Question(question) { c.boost() }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}
