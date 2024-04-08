package com.example.interviewpractice.frontend.views.search

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.Loader
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.views.mainview.Router
import com.example.interviewpractice.helpers.navToQuestion
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Tag

@Composable
//@Preview
fun SearchView(c: QuestionController, mm: MainModel, r: Router) {
    val vm: SearchViewModel = viewModel()

    LaunchedEffect(Unit){
        vm.addModel(mm)
        c.fetchData(FetchType.SEARCH)
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

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Search and Add Questions",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                "Note: prefix search only",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                ) {
                    IconButton(
                        onClick = {
                            if (vm.currentlyFilteredBy.isNotEmpty())c.search(vm.search, filters = vm.currentlyFilteredBy, completed = vm.isCompletedFilter)
                            else c.search(vm.search, completed = vm.isCompletedFilter)

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
                        onDone = { c.search(vm.search, completed = vm.isCompletedFilter) }
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { vm.isFilterOptionsVisible.value = !vm.isFilterOptionsVisible.value },
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
                    onClick = { vm.isCompletedFilter = !vm.isCompletedFilter },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Text(if (vm.isCompletedFilter)  "☑ Completed" else "☐ Completed",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    )
                }
            }
            if (vm.isFilterOptionsVisible.value) {
                FilterOptions(vm, c)
            } else {
                val filteredTagsText = buildString {
                    append("Current filtered tags: ")
                    if (vm.currentlyFilteredBy.isNotEmpty()) {
                        vm.currentlyFilteredBy.forEachIndexed { index, value ->
                            if (index > 0) {
                                append(", ")
                            }
                            append(value.v)
                        }
                    } else {
                        append("None")
                    }
                }

                Text(text = filteredTagsText)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Not here?", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            ))
            Button(
                onClick = { r.goToMakeQuestion() },
                modifier = Modifier
                    .height(50.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Add new question",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
            val scrollState = rememberScrollState()
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                if (vm.localLoading) Loader()
                for (question in vm.searchResults) {
                    Question(question.first,question.second) { navToQuestion(r.goToAnswerQuestion,question.first,c) }
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterOptions(vm: SearchViewModel = viewModel(), c: QuestionController) {
//    fun search(){
//        if (vm.currentlyFilteredBy.isNotEmpty())c.search(vm.search, vm.currentlyFilteredBy)
//        else c.search(vm.search,, completed = vm.isCompletedFilter)
//    }
    Box(
        modifier = Modifier
            .offset(y = (0).dp, x = (-95).dp)
            .rotate(45f)
            .background(Color.LightGray)
            .size(20.dp)
            .shadow(8.dp),
    )
    Box(
        modifier = Modifier
            .offset(y = (-10).dp)
            .shadow(8.dp)
            .width(350.dp)
            .height(200.dp)
            .background(Color.LightGray)
    ) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .fillMaxSize()
                .shadow(8.dp),
        ) {
            Column(
                horizontalAlignment = AbsoluteAlignment.Left,
                modifier = Modifier
                    .background(Color.LightGray)
            ) {
                Text(" Filter by tags:")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    FlowRow(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.BIOLOGY),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.BIOLOGY))
                                    vm.currentlyFilteredBy.remove(Tag.BIOLOGY)
                                else vm.currentlyFilteredBy.add(Tag.BIOLOGY)

                            },
                            label = { Text("Biology") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.ENGLISH),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.ENGLISH))
                                    vm.currentlyFilteredBy.remove(Tag.ENGLISH)
                                else vm.currentlyFilteredBy.add(Tag.ENGLISH)

                            },
                            label = { Text("English") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.CHEMISTRY),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.CHEMISTRY))
                                    vm.currentlyFilteredBy.remove(Tag.CHEMISTRY)
                                else vm.currentlyFilteredBy.add(Tag.CHEMISTRY)

                            },
                            label = { Text("Chemistry") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.ART),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.ART))
                                    vm.currentlyFilteredBy.remove(Tag.ART)
                                else vm.currentlyFilteredBy.add(Tag.ART)

                            },
                            label = { Text("Art") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.CS),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.CS))
                                    vm.currentlyFilteredBy.remove(Tag.CS)
                                else vm.currentlyFilteredBy.add(Tag.CS)

                            },
                            label = { Text("Computer Science") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.MATH),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.MATH))
                                    vm.currentlyFilteredBy.remove(Tag.MATH)
                                else vm.currentlyFilteredBy.add(Tag.MATH)

                            },
                            label = { Text("Math") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.FINANCE),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.FINANCE))
                                    vm.currentlyFilteredBy.remove(Tag.FINANCE)
                                else vm.currentlyFilteredBy.add(Tag.FINANCE)

                            },
                            label = { Text("Finance") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.PHYSICS),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.PHYSICS))
                                    vm.currentlyFilteredBy.remove(Tag.PHYSICS)
                                else vm.currentlyFilteredBy.add(Tag.PHYSICS)

                            },
                            label = { Text("Physics") })
                        FilterChip(
                            selected = vm.currentlyFilteredBy.contains(Tag.BUSINESS),
                            onClick = {
                                if (vm.currentlyFilteredBy.contains(Tag.BUSINESS))
                                    vm.currentlyFilteredBy.remove(Tag.BUSINESS)
                                else vm.currentlyFilteredBy.add(Tag.BUSINESS)
                            },
                            label = { Text("Business") })
                    }
                }
            }
        }
    }
}
