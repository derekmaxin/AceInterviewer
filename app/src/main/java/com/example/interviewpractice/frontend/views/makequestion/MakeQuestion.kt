package com.example.interviewpractice.frontend.views.makequestion

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.views.leaderboard.LeaderboardViewModel
import com.example.interviewpractice.model.MainModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MakeQuestionScreen(mm: MainModel, questionController: QuestionController, goToHome: () -> Unit)
{
    val vm: MakeQuestionViewModel = viewModel()
    vm.addModel(mm)
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = vm.questionText,
                onValueChange = { vm.questionText = it},
                label = { Text("Type your question here")},
                maxLines = 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    //imeAction = ImeAction.Done
                )
            )

            Column(
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                Text("Type:")
                Row(

                ) {
                    Checkbox(
                        checked = vm.hasText,
                        onCheckedChange = { vm.hasText = it }
                    )
                    Text(
                        text = "Accept text answers",
                        modifier = Modifier
                            .align(Alignment.CenterVertically))
                }
                Row(

                ) {
                    Checkbox(
                        checked = vm.hasVoice,
                        onCheckedChange = { vm.hasVoice = it }
                    )
                    Text(
                        text = "Accept audio answers",
                        modifier = Modifier
                            .align(Alignment.CenterVertically))
                }
            }

            Column(
                horizontalAlignment = AbsoluteAlignment.Left
            ) {
                Text("Tags:")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(155.dp)
                        .border(width = 3.dp, color = Color.LightGray)
                ) {
                    FlowRow(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        FilterChip(
                            selected = vm.biologyTag,
                            onClick = { vm.biologyTag = !vm.biologyTag },
                            label = { Text("Biology") })
                        FilterChip(
                            selected = vm.englishTag,
                            onClick = { vm.englishTag = !vm.englishTag },
                            label = { Text("English") })
                        FilterChip(
                            selected = vm.chemistryTag,
                            onClick = { vm.chemistryTag = !vm.chemistryTag },
                            label = { Text("Chemistry") })
                        FilterChip(
                            selected = vm.artTag,
                            onClick = { vm.artTag = !vm.artTag },
                            label = { Text("Art") })
                        FilterChip(
                            selected = vm.computerScienceTag,
                            onClick = { vm.computerScienceTag = !vm.computerScienceTag },
                            label = { Text("Computer Science") })
                        FilterChip(
                            selected = vm.mathTag,
                            onClick = { vm.mathTag = !vm.mathTag },
                            label = { Text("Math") })
                        FilterChip(
                            selected = vm.financeTag,
                            onClick = { vm.financeTag = !vm.financeTag },
                            label = { Text("Finance") })
                        FilterChip(
                            selected = vm.physicsTag,
                            onClick = { vm.physicsTag = !vm.physicsTag },
                            label = { Text("Physics") })
                        FilterChip(
                            selected = vm.businessTag,
                            onClick = { vm.businessTag = !vm.businessTag },
                            label = { Text("Business") })
                    }
                }
            }

            Button(
                onClick = {
                    vm.clearFields()
                    goToHome()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent)
            ) {
                Text("Discard Draft",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Red,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .padding(vertical = 0.dp),
                )
            }

            Button(
                onClick = {
                    questionController.verifyNewQuestion(
                        vm.questionText,
                        vm.hasVoice,
                        vm.hasText,
                        vm.makeTagList(),
                        goToHome)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 4.dp)
            ) {
                Text("Submit Question",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                )
            }
        }
    }
}