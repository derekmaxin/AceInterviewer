package com.example.interviewpractice

import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UserException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class ControllerTest {
        private lateinit var ac: AuthController
        private lateinit var uc: UserController
        private lateinit var qc: QuestionController
        private lateinit var rc: ReviewController
        private lateinit var nc: NotificationController
        private lateinit var hc: HistoryController

        private val mainModel: MainModel = Mockito.mock(MainModel::class.java)
        private val authModel: AuthModel = Mockito.mock(AuthModel::class.java)


        @Before
        fun setup() {
            ac = AuthController(mainModel,authModel)
            uc = UserController(mainModel,authModel)
            qc = QuestionController(mainModel,authModel)
            rc = ReviewController(mainModel,authModel)
            nc = NotificationController(mainModel,authModel)
            hc = HistoryController(mainModel, authModel)

            Dispatchers.setMain(Dispatchers.Unconfined)

        }

        //NOTIFICATIONCONTROLLER
        @Test(expected = Exception::class)
        fun listenForNotificationsSuccessThrow() {
            //Arrange
            `when`(authModel.getUserID()).thenReturn("123456")
            `when`(mainModel.addNotificationListener("123456")).thenThrow(Exception("Test"))
            var isCorrect = false;

            //Act
            try {
                nc.listenForNotifications()
            }
            catch(e: Exception) {
                //Should throw and exception
                isCorrect = true
            }

            //Assert
            assert(isCorrect)
        }

    @Test
    fun removeNotificationsThrow() {
        //Arrange
        `when`(authModel.getUserID()).thenReturn("123456")
        `when`(mainModel.addNotificationListener("123456")).thenThrow(Exception("Test"))
        var isCorrect = false

        //Act
        try {
            val x = nc.listenForNotifications()
        }
        catch(e: Exception) {
            //Should throw and exception
            isCorrect = true
        }

        //Assert
        assert(isCorrect)
    }
//
//        @Test(expected = UserException::class)
//        fun passwordBlank() {
//            authcontroller.verifyPasswordFormat("")
//        }
//        @Test
//        fun passwordPerfect() {
//            authcontroller.verifyPasswordFormat("pass06")
//        }
//
//        @Test(expected = UserException::class)
//        fun passwordsDoNotMatch() {
//            authcontroller.verifyConfirm("123456", "654321")
//        }
//
//        @Test(expected = UserException::class)
//        fun emailBlank() {
//            authcontroller.verifyEmailFormat("")
//        }
//
//        @Test
//        fun emailPerfect() {
//            authcontroller.verifyEmailFormat("ryan@gmail.com")
//        }
//
//        @Test(expected = UserException::class)
//        fun birthdayInvalidUnder16() {
//            authcontroller.verifyBirthday(Date())
//        }
//
//        @Test(expected = UserException::class)
//        fun noInterestsSelected() {
//            authcontroller.verifyFOI(emptySet())
//        }
//

        @After
        fun teardown() {
            Dispatchers.resetMain()
        }
//    }
}