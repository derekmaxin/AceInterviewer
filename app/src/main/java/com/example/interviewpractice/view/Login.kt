package com.example.interviewpractice.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.R

@Composable
@Preview
fun LoginScreen() {
    var username by remember {mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    Surface(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.klee),
                contentDescription = "Your Image",
                modifier = Modifier.fillMaxWidth().height(500.dp).padding(vertical = 8.dp),
            )
            TextField(
                value = username,
//                placeholder = {Text("username")},
                onValueChange = {username = it },
                label = { Text("username") },
                modifier = Modifier.fillMaxWidth().height(85.dp).padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { }
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = password,
//                placeholder = {Text("password")},
                onValueChange = {password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().height(85.dp).padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                ),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            Button(
                onClick = {/*verifyRegister()*/ },
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(vertical = 8.dp)
            ) {
                Text("Log in",
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = Color.White,
                    )
                )
            }
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp),
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
