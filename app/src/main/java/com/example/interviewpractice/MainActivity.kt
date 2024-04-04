package com.example.interviewpractice

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
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
import com.example.interviewpractice.frontend.views.search.makequestion.MakeQuestionViewModel
import com.example.interviewpractice.frontend.views.notifications.NotificationsViewModel
import com.example.interviewpractice.frontend.views.profile.ProfileViewModel
import com.example.interviewpractice.frontend.views.profile.bestquestions.BestQuestionsViewModel
import com.example.interviewpractice.frontend.views.review.ReviewViewViewModel
import com.example.interviewpractice.frontend.views.search.SearchViewModel
import com.example.interviewpractice.model.MainModel
import android.Manifest
import android.util.Log
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.controller.ReviewController

class MainActivity : ComponentActivity() {

    //NOTIFICATIONS
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }


    private lateinit var authModel: AuthModel
    private lateinit var mainModel: MainModel
    private lateinit var mainVM: MainViewModel

    private lateinit var ac: AuthController
    private lateinit var uc: UserController
    private lateinit var qc: QuestionController
    private lateinit var rc: ReviewController
    private lateinit var nc: NotificationController
    private lateinit var hc: HistoryController

    private lateinit var aaa: BestQuestionsViewModel


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity","App started")
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //Ask for notifications
        askNotificationPermission()


        //Initialize views and models
        authModel = AuthModel()
        mainModel = MainModel()

        mainVM = MainViewModel()
        mainVM.addModel(authModel)

        authModel.initAuth()
        mainModel.invalidateAll()


        ac = AuthController(mainModel,authModel)
        uc = UserController(mainModel,authModel)
        qc = QuestionController(mainModel,authModel)
        rc = ReviewController(mainModel,authModel)
        nc = NotificationController(mainModel,authModel)
        hc = HistoryController(mainModel, authModel)


        aaa = BestQuestionsViewModel()
        aaa.addModel(mainModel)



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
                        qc=qc,
                        rc=rc,
                        nc=nc,
                        hc=hc,
                        bestQuestionsViewModel = aaa
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