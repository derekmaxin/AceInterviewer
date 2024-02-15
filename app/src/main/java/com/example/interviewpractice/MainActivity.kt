package com.example.interviewpractice

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.ui.theme.InterviewPracticeTheme
import com.example.interviewpractice.view.auth.Loading
import com.example.interviewpractice.view.auth.LoginScreen
import com.example.interviewpractice.view.auth.RegisterScreen
import com.example.interviewpractice.viewmodel.auth.LoginViewModel
import com.example.interviewpractice.viewmodel.auth.RegisterViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.interviewpractice.view.auth.MainView
import com.example.interviewpractice.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var model: Model
    private lateinit var controller: UserController

    private lateinit var registerVM: RegisterViewModel
    private lateinit var loginVM: LoginViewModel
    private lateinit var mainVM: MainViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initialize views and models
        model = Model()
        controller = UserController(model)
        registerVM = RegisterViewModel(model)
        loginVM = LoginViewModel(model)
        mainVM = MainViewModel(model)
        model.initAuth()


        setContent {
            InterviewPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainView(registerViewModel = registerVM, loginViewModel = loginVM, mainViewModel = mainVM, controller=controller)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InterviewPracticeTheme {
        Greeting("Android")
    }
}