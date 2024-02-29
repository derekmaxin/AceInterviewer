package com.example.interviewpractice.frontend.views.mainview

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interviewpractice.Greeting
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.frontend.views.home.HomeScreen
import com.example.interviewpractice.frontend.views.auth.login.LoginViewModel
import com.example.interviewpractice.frontend.views.auth.register.RegisterViewModel
import com.example.interviewpractice.frontend.views.auth.Loading
import com.example.interviewpractice.frontend.views.auth.login.LoginScreen
import com.example.interviewpractice.frontend.views.auth.register.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun MainView(registerViewModel: RegisterViewModel, loginViewModel: LoginViewModel, mainViewModel: MainViewModel, controller: UserController) {
    val registerVM by remember { mutableStateOf(registerViewModel) }
    val loginVM by remember { mutableStateOf(loginViewModel) }
    val mainVM by remember { mutableStateOf(mainViewModel) }

    var loggingIn by remember {mutableStateOf(false)}

    // NavController //////////////////////////////////////////////////////////

    val unauthenticatedNavController = rememberNavController()
    val authenticatedNavController = rememberNavController()


    if (mainVM.loading) {
        //Auth is loading
        Loading()
    }
    else if (mainVM.user != null) {
//        //If user is signed in
//        HomeScreen(controller = controller)
//
//        //REMOVE GREETING, THIS IS JUST TO SHOW USER EMAIL (FOR DEBUGGING PURPOSES)
//

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

            composable("register") { RegisterScreen(viewModel = registerVM, controller = controller) {
            }
            }
        }
    }


//
}
