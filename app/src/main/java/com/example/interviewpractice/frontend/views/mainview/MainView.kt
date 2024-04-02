package com.example.interviewpractice.frontend.views.mainview

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.frontend.views.home.HomeScreen
import com.example.interviewpractice.frontend.views.auth.Loading
import com.example.interviewpractice.frontend.views.auth.login.LoginScreen
import com.example.interviewpractice.frontend.views.auth.register.RegisterScreen
import androidx.compose.ui.platform.LocalContext
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.frontend.components.navbar.NavBar
import com.example.interviewpractice.frontend.views.profile.leaderboard.LeaderboardView
import com.example.interviewpractice.frontend.views.search.makequestion.MakeQuestionScreen
import com.example.interviewpractice.frontend.views.notifications.Notifications
import com.example.interviewpractice.frontend.views.profile.ProfileView
import com.example.interviewpractice.frontend.views.profile.bestquestions.BestQuestionsView
import com.example.interviewpractice.frontend.views.profile.bestquestions.BestQuestionsViewModel
import com.example.interviewpractice.frontend.views.review.ReviewView
import com.example.interviewpractice.frontend.views.search.SearchView
import com.example.interviewpractice.frontend.views.submitanswer.SubmitAnswer
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

@RequiresApi(Build.VERSION_CODES.S)
@Composable
//@Preview
fun MainView(
    vm: MainViewModel,
    uc: UserController,
    ac: AuthController,
    qc: QuestionController,
    rc: ReviewController,
    nc: NotificationController,
    am: AuthModel,
    mm: MainModel,
    bestQuestionsViewModel: BestQuestionsViewModel
    )
{
    LaunchedEffect(Unit){
        ac.fetchData(FetchType.RESETUSER)
    }
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
                ReviewView(mm=mm,c=rc)
            }
            composable("leaderboard") {
                LeaderboardView(mm=mm,c=uc)
            }
            composable("best questions") {
                BestQuestionsView(vm= bestQuestionsViewModel)
            }
            composable("make question") {
                MakeQuestionScreen(mm=mm, questionController = qc,
                    goToHome = { anc.navigate("home") })
            }
            composable("home") {
                HomeScreen(c = ac, mm=mm)
            }
            composable("notifications") {
                Notifications(mm=mm, c=nc)
            }
            composable("search") {
                SearchView(
                    c = qc, mm=mm
                ) { anc.navigate("make question") }
            }
            composable("profile") {
                ProfileView(
                    mm=mm, c = qc, ac=ac, uc=uc, goToLeaderboard = { anc.navigate("leaderboard")},
                    goToBestQuestions = {anc.navigate("best questions")})
            }
            composable("question") {
                SubmitAnswer(qc = QuestionController(am = am, mm = mm))
            }
        }
        NavBar(
            goToReviews={anc.navigate("reviews")},
            goToSearch={anc.navigate("search")},
            goToHome={anc.navigate("home")},
            goToNotifications={anc.navigate("notifications")},
            goToProfile={anc.navigate("profile")},
            goToQuestion={anc.navigate("question")},
            nc=nc,
            mm=mm)

    }
    else {
        NavHost(navController = unc, startDestination = "login") {
            composable("login") {
                LoginScreen(c = ac, am=am) { unc.navigate("register") }
            }
            composable("register") {
                RegisterScreen(am=am, c = ac)
            }
        }
    }
}
