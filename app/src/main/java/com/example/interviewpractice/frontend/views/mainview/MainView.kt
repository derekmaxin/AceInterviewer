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
import com.example.interviewpractice.frontend.views.notifications.Notifications
import com.example.interviewpractice.frontend.views.notifications.NotificationsViewModel
import com.example.interviewpractice.frontend.views.profile.ProfileView
import com.example.interviewpractice.frontend.views.profile.ProfileViewModel
import com.example.interviewpractice.frontend.views.review.ReviewView
import com.example.interviewpractice.frontend.views.review.ReviewViewViewModel
import com.example.interviewpractice.frontend.views.search.SearchView
import com.example.interviewpractice.frontend.views.search.SearchViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun MainView(authModel: AuthModel, mainModel: MainModel, mainViewModel: MainViewModel)
{
//    val registerVM by remember { mutableStateOf(registerViewModel) }
//    val loginVM by remember { mutableStateOf(loginViewModel) }
//    val reviewVM by remember {mutableStateOf(reviewViewModel)}
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
        authModel.clearError()
    }
    if (mainVM.loading) {
        //Auth is loading
        Loading()
    }
    else if (mainVM.user != null) {
        //If user is signed in

        NavHost(navController = anc, startDestination = "home") {
            composable("reviews") {
                ReviewView(ReviewViewViewModel(mainModel))
            }
            composable("leaderboard") {
                LeaderboardView()
            }
            composable("make question") {
                MakeQuestionScreen(viewModel = MakeQuestionViewModel(mainModel), questionController = QuestionController(mainModel, authModel),
                    goToHome = { anc.navigate("home") })
            }
            composable("home") {
                HomeScreen(c = AuthController(authModel), questionVM = QuestionViewModel(mainModel),
                    goToMakeQuestion = { anc.navigate("make question") })
            }
            composable("notifications") {
                Notifications(viewModel = NotificationsViewModel(mainModel))            }

            composable("search") {
                SearchView(c = QuestionController(mainModel, authModel), searchVM = SearchViewModel(mainModel),
                    goToMakeQuestion = { anc.navigate("make question") })
            }
            composable("profile") {
                ProfileView(profileViewModel = ProfileViewModel(mainModel),
                    goToLeaderboard = { anc.navigate("leaderboard") })
            }
        }
        NavBar(
            goToReviews={anc.navigate("reviews")},
            goToSearch={anc.navigate("search")},
            goToHome={anc.navigate("home")},
            goToNotifications={anc.navigate("notifications")},
            goToProfile={anc.navigate("profile")}
        )

    }
    else {
        NavHost(navController = unc, startDestination = "login") {
            composable("login") {
                LoginScreen(controller = AuthController(authModel), viewModel = LoginViewModel(authModel),
                    goToRegister ={unc.navigate("register")})
            }
            composable("register") {
                RegisterScreen(viewModel = RegisterViewModel(authModel), controller = AuthController(authModel))
            }
        }
    }
}
