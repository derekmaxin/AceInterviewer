package com.example.interviewpractice

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.ui.theme.InterviewPracticeTheme
import com.example.interviewpractice.frontend.views.auth.login.LoginViewModel
import com.example.interviewpractice.frontend.views.auth.register.RegisterViewModel
import com.example.interviewpractice.frontend.views.mainview.MainView
import com.example.interviewpractice.frontend.views.mainview.MainViewModel
import com.example.interviewpractice.frontend.views.makequestion.MakeQuestionViewModel
import com.example.interviewpractice.frontend.views.notifications.NotificationsViewModel
import com.example.interviewpractice.frontend.views.profile.ProfileViewModel
import com.example.interviewpractice.frontend.views.profile.bestquestions.BestQuestionsViewModel
import com.example.interviewpractice.frontend.views.review.ReviewViewViewModel
import com.example.interviewpractice.frontend.views.search.SearchViewModel
import com.example.interviewpractice.model.MainModel

class MainActivity : ComponentActivity() {

    private lateinit var authModel: AuthModel
    private lateinit var mainModel: MainModel

    private lateinit var authController: AuthController
    private lateinit var questionController: QuestionController

    private lateinit var registerVM: RegisterViewModel
    private lateinit var loginVM: LoginViewModel
    private lateinit var mainVM: MainViewModel
    private lateinit var searchVM: SearchViewModel
    private lateinit var notificationVM: NotificationsViewModel
    private lateinit var profileVM: ProfileViewModel
    private lateinit var reviewVM: ReviewViewViewModel

    private lateinit var questionVM: QuestionViewModel
    private lateinit var makeQuestionVM: MakeQuestionViewModel
    private lateinit var bestQuestionVM: BestQuestionsViewModel
    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Initialize views and models
        authModel = AuthModel()
        mainModel = MainModel()

        authController = AuthController(authModel)
        questionController = QuestionController(mm = mainModel, am = authModel)

        registerVM = RegisterViewModel(authModel)
        loginVM = LoginViewModel(authModel)
        mainVM = MainViewModel(authModel)
        searchVM = SearchViewModel(mainModel)
        notificationVM = NotificationsViewModel(mainModel)
        profileVM = ProfileViewModel(mainModel)
        reviewVM = ReviewViewViewModel(mainModel)

        questionVM = QuestionViewModel(mainModel)
        makeQuestionVM = MakeQuestionViewModel(mainModel)
        bestQuestionVM = BestQuestionsViewModel(mainModel)

        authModel.initAuth()


        setContent {
            InterviewPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainView(registerViewModel = registerVM,
                        loginViewModel = loginVM,
                        mainViewModel = mainVM,
                        authController= authController,
                        searchViewModel = searchVM,
                        questionViewModel = questionVM,
                        questionController= questionController,
                        makeQuestionViewModel = makeQuestionVM,
                        notificationsViewModel = notificationVM,
                        profileViewModel = profileVM,
                        bestQuestionsViewModel = bestQuestionVM,
                        reviewViewModel = reviewVM
                        )
                    {authModel.clearError()}
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