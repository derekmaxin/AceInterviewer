package com.example.interviewpractice.frontend.views.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.R
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.types.FetchType


@Composable
fun LoginScreen(
    am: AuthModel,
    c: AuthController,
    goToRegister: () -> Unit)
{
    val vm: LoginViewModel = viewModel()
    LaunchedEffect(Unit){
        vm.addModel(am)
        c.fetchData(FetchType.FENCE)
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.unsubscribe()
        }
    }
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.aceinterviewerlogo),
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            Button(
                onClick = {c.verifyForgotPassword(vm.email) },
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
                onClick = {c.verifySignIn(password = vm.password, email = vm.email)},
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
                //onClick = onSwitch,
                onClick = goToRegister,
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
