package com.example.interviewpractice.frontend.views.makequestion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MakeQuestionScreen(viewModel: MakeQuestionViewModel, goToHome: () -> Unit)
{
    val vm by remember { mutableStateOf(viewModel) }
//    val tagList by remember { mutatableStateListOf<String>() }
//    val tagList = listOf("Biology", "English", "Chemistry", "Art", "Computer Science", "Math"
//        , "Finance", "Physics", "Business")
//    var tagText = remember { mutableStateOf("") }

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
                        checked = vm.textAnswer,
                        onCheckedChange = { vm.textAnswer = it }
                    )
                    Text(
                        text = "Accept text answers",
                        modifier = Modifier
                            .align(Alignment.CenterVertically))
                }
                Row(

                ) {
                    Checkbox(
                        checked = vm.voiceAnswer,
                        onCheckedChange = { vm.voiceAnswer = it }
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
                        .height(50.dp)
                        .border(width = 3.dp, color = Color.LightGray)
                ) {
                    FlowRow(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        FilterChip(
                            selected = vm.selectedTag1,
                            onClick = { vm.selectedTag1 = !vm.selectedTag1 },
                            label = { Text("Tag 1") })
                        FilterChip(
                            selected = vm.selectedTag2,
                            onClick = { vm.selectedTag2 = !vm.selectedTag2 },
                            label = { Text("Tag 2") })
                    }
                }
            }

            Button(
                onClick = goToHome,
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
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(vertical = 4.dp)
                    .background(Color.Green)
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
