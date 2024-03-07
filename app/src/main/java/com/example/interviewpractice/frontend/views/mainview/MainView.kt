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
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.frontend.views.home.HomeScreen
import com.example.interviewpractice.frontend.views.auth.login.LoginViewModel
import com.example.interviewpractice.frontend.views.auth.register.RegisterViewModel
import com.example.interviewpractice.frontend.views.auth.Loading
import com.example.interviewpractice.frontend.views.auth.login.LoginScreen
import com.example.interviewpractice.frontend.views.auth.register.RegisterScreen
import androidx.compose.ui.platform.LocalContext
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.NavBar
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.views.leaderboard.LeaderboardView
import com.example.interviewpractice.frontend.views.makequestion.MakeQuestionScreen
import com.example.interviewpractice.frontend.views.makequestion.MakeQuestionViewModel
import com.example.interviewpractice.frontend.views.profile.ProfileView
import com.example.interviewpractice.frontend.views.review.ReviewView
import com.example.interviewpractice.frontend.views.search.SearchView
import com.example.interviewpractice.frontend.views.search.SearchViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun MainView(
    registerViewModel: RegisterViewModel,
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel,
    questionViewModel: QuestionViewModel,
    authController: AuthController,
    questionController: QuestionController,
    makeQuestionViewModel: MakeQuestionViewModel,
    clearError: () -> Unit)
{
    val registerVM by remember { mutableStateOf(registerViewModel) }
    val loginVM by remember { mutableStateOf(loginViewModel) }
    val mainVM by remember { mutableStateOf(mainViewModel) }


    // NavController //////////////////////////////////////////////////////////

    val unc = rememberNavController()
    val anc = rememberNavController()

    mainVM.error?.let {err ->

        //TODO: Work with snackbars instead of Toasts. This is just a placeholder
        val text = err.message
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
        //If user is signed in

        NavHost(navController = anc, startDestination = "home") {
            composable("reviews") {
                ReviewView(questionVM = questionViewModel)
            }
            composable("leaderboard") {
                LeaderboardView()
            }
            composable("make question") {
                MakeQuestionScreen(viewModel = makeQuestionViewModel,
                    goToHome = { anc.navigate("home") })
            }
            composable("home") {
                HomeScreen(c = authController, questionVM = questionViewModel,
                    goToMakeQuestion = { anc.navigate("make question") })
            }
            composable("search") {
                SearchView(c = questionController, searchVM = searchViewModel)
            }
            composable("profile") {
                ProfileView(goToLeaderboard = { anc.navigate("leaderboard") })
            }
        }
        NavBar(
            goToReviews={anc.navigate("reviews")},
            goToSearch={anc.navigate("search")},
            goToHome={anc.navigate("home")},
//                goToNotfications={anc.navigate("notifications")},
            goToProfile={anc.navigate("profile")}
        )

    }
    else {
        NavHost(navController = unc, startDestination = "login") {
            composable("login") { LoginScreen(controller = authController, viewModel = loginVM, onNavigateToRegister ={unc.navigate("register")}) }
            composable("register") { RegisterScreen(viewModel = registerVM, controller = authController) }
        }
    }
}
