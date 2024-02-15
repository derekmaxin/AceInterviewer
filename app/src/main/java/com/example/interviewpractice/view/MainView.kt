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
import androidx.compose.runtime.setValue
import com.example.interviewpractice.Greeting
import com.example.interviewpractice.GreetingPreview
import com.example.interviewpractice.R
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.view.HomeScreen
import com.example.interviewpractice.viewmodel.auth.LoginViewModel
import com.example.interviewpractice.viewmodel.auth.MainViewModel
import com.example.interviewpractice.viewmodel.auth.RegisterViewModel

@Composable
//@Preview
fun MainView(registerViewModel: RegisterViewModel, loginViewModel: LoginViewModel, mainViewModel: MainViewModel, controller: UserController) {
    val registerVM by remember { mutableStateOf(registerViewModel) }
    val loginVM by remember { mutableStateOf(loginViewModel) }
    val mainVM by remember { mutableStateOf(mainViewModel) }

    var loggingIn by remember {mutableStateOf(false)}

    if (mainVM.loading) {
        //Auth is loading
        Loading()
    }
    else if (mainVM.user != null) {
        //If user is signed in
        HomeScreen(controller = controller)

        //REMOVE GREETING, THIS IS JUST TO SHOW USER EMAIL (FOR DEBUGGING PURPOSES)
        Greeting(mainVM.user!!.email!!)
    }
    else {
        //No user is logged in
        if (loggingIn) {
            LoginScreen(viewModel = loginVM, controller = controller) {loggingIn = !loggingIn}
        }
        else {
            RegisterScreen(viewModel = registerVM, controller = controller) {loggingIn = !loggingIn}
        }


    }
}
