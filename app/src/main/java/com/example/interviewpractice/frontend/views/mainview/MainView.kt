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
import com.example.interviewpractice.controller.ProfileController
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
fun MainView(am: AuthModel, mm: MainModel, vm: MainViewModel)
{

    // NavController //////////////////////////////////////////////////////////
    val unc = rememberNavController()
    val anc = rememberNavController()

    vm.error?.let {err ->

        //TODO: Work with snackbars instead of Toasts. This is just a placeholder
        val text = err.message
        val duration = Toast.LENGTH_LONG

        val toast = Toast.makeText(LocalContext.current, text, duration) // in Activity
        toast.show()
        am.clearError()
    }
    if (vm.loading) {
        //Auth is loading
        Loading()
    }
    else if (vm.user != null) {
        //If user is signed in

        NavHost(navController = anc, startDestination = "home") {
            composable("reviews") {
                ReviewView(ReviewViewViewModel(mm))
            }
            composable("leaderboard") {
                LeaderboardView()
            }
            composable("make question") {
                MakeQuestionScreen(viewModel = MakeQuestionViewModel(mm), questionController = QuestionController(mm, am),
                    goToHome = { anc.navigate("home") })
            }
            composable("home") {
                HomeScreen(c = AuthController(mm,am), questionVM = QuestionViewModel(mm),
                    goToMakeQuestion = { anc.navigate("make question") })
            }
            composable("notifications") {
                Notifications(viewModel = NotificationsViewModel(mm))            }

            composable("search") {
                SearchView(c = QuestionController(mm, am), searchVM = SearchViewModel(mm),
                    goToMakeQuestion = { anc.navigate("make question") })
            }
            composable("profile") {
                ProfileView(vm = ProfileViewModel(mm), c = ProfileController(mm,am),
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
                LoginScreen(c = AuthController(mm,am), vm = LoginViewModel(am),
                    goToRegister ={unc.navigate("register")})
            }
            composable("register") {
                RegisterScreen(vm = RegisterViewModel(am), c = AuthController(mm,am))
            }
        }
    }
}
