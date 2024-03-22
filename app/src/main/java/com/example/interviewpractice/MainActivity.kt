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
import com.example.interviewpractice.controller.UserController
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
import com.example.interviewpractice.frontend.views.review.ReviewViewViewModel
import com.example.interviewpractice.frontend.views.search.SearchViewModel
import com.example.interviewpractice.model.MainModel

class MainActivity : ComponentActivity() {

    private lateinit var authModel: AuthModel
    private lateinit var mainModel: MainModel
    private lateinit var mainVM: MainViewModel

    private lateinit var ac: AuthController
    private lateinit var uc: UserController
    private lateinit var qc: QuestionController

    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Initialize views and models
        authModel = AuthModel()
        mainModel = MainModel()

        mainVM = MainViewModel()
        mainVM.addModel(authModel)
        authModel.initAuth()

        ac = AuthController(mainModel,authModel)
        uc = UserController(mainModel,authModel)
        qc = QuestionController(mainModel,authModel)



        setContent {
            InterviewPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainView(
                        am = authModel,
                        mm = mainModel,
                        vm = mainVM,
                        ac=ac,
                        uc=uc,
                        qc=qc
                    )
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