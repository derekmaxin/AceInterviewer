package com.example.interviewpractice.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                modifier = Modifier.fillMaxWidth().height(450.dp).padding(vertical = 8.dp),
            )
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth().height(85.dp).padding(vertical = 8.dp),
                textStyle = TextStyle(fontSize = 28.sp)
            )
            TextField(
                value = "",
                onValueChange = { },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().height(85.dp).padding(vertical = 8.dp),
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
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(80.dp).padding(vertical = 8.dp)
            ) {
                Text("Log in",
                    style = TextStyle(
                        fontSize = 28.sp,
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
