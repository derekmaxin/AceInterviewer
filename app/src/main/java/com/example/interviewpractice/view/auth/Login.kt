package com.example.interviewpractice.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.R
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel, controller: UserController, onSwitch: () -> Unit ) {
    val vm by remember { mutableStateOf(viewModel) }
    val c by remember { mutableStateOf(controller) }

//    var username by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }



    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.klee),
                contentDescription = "Your Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(vertical = 8.dp),
            )
            TextField(
                value = vm.email,
                onValueChange = {vm.email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { }
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = vm.password,
                onValueChange = {vm.password = it },
                label = { Text("Password") },
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
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent)
            ) {
                Text("Forgot Password?",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Magenta,
                    ),
                    modifier = Modifier.fillMaxWidth().height(20.dp).padding(vertical = 0.dp),
                )
            }
            Button(
                onClick = {c.verifySignIn(password = vm.password, email = vm.email) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text("Log in",
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.White,
                    )
                )
            }
            Button(
                onClick = onSwitch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent) //containerColor vs backgroundColor
            ) {
                Text("Register",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Black,
                    )
                )
            }
        }
    }
}
