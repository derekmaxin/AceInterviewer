package com.example.interviewpractice.frontend.views.mainview

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interviewpractice.Greeting
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.frontend.views.home.HomeScreen
import com.example.interviewpractice.frontend.views.auth.login.LoginViewModel
import com.example.interviewpractice.frontend.views.auth.register.RegisterViewModel
import com.example.interviewpractice.frontend.views.auth.Loading
import com.example.interviewpractice.frontend.views.auth.login.LoginScreen
import com.example.interviewpractice.frontend.views.auth.register.RegisterScreen
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun MainView(registerViewModel: RegisterViewModel, loginViewModel: LoginViewModel, mainViewModel: MainViewModel, controller: AuthController, clearError: () -> Unit) {
    val registerVM by remember { mutableStateOf(registerViewModel) }
    val loginVM by remember { mutableStateOf(loginViewModel) }
    val mainVM by remember { mutableStateOf(mainViewModel) }

    // NavController //////////////////////////////////////////////////////////

    val unauthenticatedNavController = rememberNavController()
    val authenticatedNavController = rememberNavController()

    mainVM.error?.let {err ->

        //TODO: Work with snackbars instead of Toasts. This is just a placeholder
        val text = "${err.message}"
        val duration = Toast.LENGTH_LONG

        val toast = Toast.makeText(LocalContext.current, text, duration) // in Activity
        toast.show()
        clearError()
    }
    if (mainVM.loading) {
        //Auth is loading
        Loading()
    }
    else if (mainVM.user != null) {
//        //If user is signed in

        NavHost(navController = authenticatedNavController, startDestination = "home") {
            composable("home") {
                HomeScreen(controller = controller)
                Greeting(mainVM.user!!.email!!)
            }
        }
    }
    else {
        NavHost(navController = unauthenticatedNavController, startDestination = "login") {
            composable("login") { LoginScreen(controller = controller, viewModel = loginVM, onNavigateToRegister ={unauthenticatedNavController.navigate("register")}) }
            composable("register") { RegisterScreen(viewModel = registerVM, controller = controller) }
        }
    }
}
