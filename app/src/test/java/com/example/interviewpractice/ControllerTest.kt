package com.example.interviewpractice

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.controller.ReviewController
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.FetchType
import org.mockito.kotlin.whenever
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import junit.framework.TestCase.assertEquals
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
import org.mockito.exceptions.base.MockitoException
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.io.File
import java.util.Date
import java.util.UUID
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import junit.framework.TestCase.assertTrue
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.inOrder

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

        //NOTIFICATION CONTROLLER
        @Test
        fun listenForNotificationsSuccessThrow() {
            //Arrange
            `when`(authModel.getUserID()).thenReturn("123456")
            `when`(mainModel.addNotificationListener("123456")).thenThrow(RuntimeException("Test"))
            var isCorrect = true;

            //Act
            try {
                nc.listenForNotifications()
            }
            catch(e: Exception) {
                //Should throw and exception
                isCorrect = false
            }

            //Assert
            assert(isCorrect)
        }

    @Test
    fun removeNotificationsThrow() = runTest {
        //Arrange
        `when`(authModel.getUserID()).thenReturn("123456")
        `when`(mainModel.addNotificationListener("123456")).thenThrow(RuntimeException("Test"))
        var isCorrect = true

        //Act
        try {
            nc.listenForNotifications()
        }
        catch(e: Exception) {
            //Should not leak exceptions
            isCorrect = false
        }

        //Assert
        assert(isCorrect)
    }
    
    //AUTH CONTROLLER
    @Test
    fun usernameBlank() {
        //arrange
        val username = ""
        var isCorrect : Boolean

        //act
        try {
            ac.verifyUsernameFormat(username)
            isCorrect = false
        } catch (e : Exception) {
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun usernamePerfect() {
        //arrange
        val username = "testuser1"
        var isCorrect : Boolean

        //act
        try {
            ac.verifyUsernameFormat(username)
            isCorrect = true
        } catch (e : Exception) {
            isCorrect = false
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun passwordBlank() {
        //arrange
        val text = ""
        var isCorrect : Boolean

        //act
        try {
            ac.verifyPasswordFormat(text)
            isCorrect = false
        } catch (e: Exception) {
            //should throw an exception
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }
    @Test
    fun passwordPerfect() {
        //arrange
        val text = "pass06"
        var isCorrect : Boolean

        //act
        try {
            ac.verifyPasswordFormat(text)
            isCorrect = true
        } catch (e: Exception) {
            isCorrect = false
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun passwordsDoNotMatch() {
        //arrange
        val password = "123456"
        val confirm = "654321"
        var isCorrect : Boolean

        //act
        try {
            ac.verifyConfirm(password, confirm)
            isCorrect = false
        } catch (e: Exception) {
            isCorrect = true
        }

        //asset
        assert(isCorrect)
    }

    @Test
    fun emailBlank() {
        //arrange
        val email = ""
        var isCorrect : Boolean

        //act
        try {
            ac.verifyEmailFormat(email)
            isCorrect = false
        } catch (e: Exception) {
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun emailPerfect() {
        //arrange
        val email = "user@gmail.com"
        var isCorrect : Boolean

        //act
        try {
            ac.verifyEmailFormat(email)
            isCorrect = true
        } catch (e: Exception) {
            isCorrect = false
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun birthdayInvalidUnder16() {
        //arrange
        val date = Date()
        var isCorrect: Boolean

        //act
        try {
            ac.verifyBirthday(date)
            isCorrect = false
        } catch (e: Exception) {
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }

    @Test
    fun noInterestsSelected() {
        //arrange
        var isCorrect: Boolean

        //act
        try {
            ac.verifyFOI(emptySet())
            isCorrect = false
        } catch (e: Exception) {
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }

    //QUESTION CONTROLLER
    @Test
    fun `test verifyAndAddNewQuestion adds question successfully`() = runTest {
        // Arrange
        val questionText = "What is Mockito?"
        val tagList = listOf(Tag.CS)
        val userID = "user123"
        Mockito.`when`(authModel.getUserID()).thenReturn(userID)
//        Mockito.doNothing().`when`(mainModel).addQuestion(any())

        // Act
        qc.verifyAndAddNewQuestion(questionText, tagList, {})

        // Assert
        argumentCaptor<Question>().apply {
            Mockito.verify(mainModel).addQuestion(capture())
            assertEquals(questionText, firstValue.questionText)
            assertEquals(tagList, firstValue.tags)
            assertEquals(userID, firstValue.userID)
        }
    }

    @Test
    fun testLoadNextQuestion_Success() = runTest {
        // Arrange
        val question = Question(questionText = "Next Question", tags = listOf(Tag.MATH), userID = UUID.randomUUID().toString())

        // Act
        qc.loadNextQuestion(question)

        // Assert
        Mockito.verify(mainModel).currentQuestionData = question
    }

    @Test
    fun testSearch_Success() = runTest {
        // Arrange
        val queryText = "Sample Query"
        val completed = false
        val filters = listOf(Tag.PHYSICS)
        val userID = UUID.randomUUID().toString()
        Mockito.`when`(authModel.getUserID()).thenReturn(userID)

        // Act
        qc.search(queryText, completed, filters)

        // Assert
        Mockito.verify(mainModel).searchQuestion(queryText, userID, filters, completed)
    }

    @Test
    fun testSearchBestQuestions_Success() = runTest {
        // Arrange

        // Act
        qc.searchBestQuestions()

        // Assert
        Mockito.verify(mainModel).searchUserQuestion()
    }

    //Controller
    @Test
    fun `fetchData PROFILE calls getCurrentUserData`() = runTest {
        // Arrange
        whenever(mainModel.check(FetchType.PROFILE)).thenReturn(true)

        // Act
        qc.fetchData(FetchType.PROFILE)

        // Assert
        verify(mainModel).getCurrentUserData()
    }

    @Test
    fun `fetchData LEADERBOARD calls getLeaderboardData`() = runTest {
        // Arrange
        whenever(mainModel.check(FetchType.LEADERBOARD)).thenReturn(true)

        // Act
        qc.fetchData(FetchType.LEADERBOARD)

        // Assert
        verify(mainModel).getLeaderboardData()
    }

    @Test
    fun `fetchData SEARCH calls searchQuestion when not checked`() = runTest {
        // Arrange
        whenever(mainModel.check(FetchType.SEARCH)).thenReturn(false)
        whenever(authModel.getUserID()).thenReturn("userID")

        // Act
        qc.fetchData(FetchType.SEARCH)

        // Assert
        verify(mainModel).searchQuestion("", "userID")
    }

    @Test
    fun `fetchData RECOMMENDATION searches question with self flag when recommendations are null`() = runTest {
        // Arrange
        whenever(mainModel.homePageRecommendations).thenReturn(null)
        whenever(authModel.getUserID()).thenReturn("userID")

        // Act
        qc.fetchData(FetchType.RECOMMENDATION)

        // Assert
        verify(mainModel).searchQuestion("", "userID", self = true)
    }

    @Test
    fun `fetchData NOTIFICATION calls getNotificationData`() = runTest {
        // Arrange
        whenever(authModel.getUserID()).thenReturn("userID")

        // Act
        qc.fetchData(FetchType.NOTIFICATION)

        // Assert
        verify(mainModel).getNotificationData("userID")
    }

    @Test
    fun `fetchData HISTORY calls getHistoryData`() = runTest {
        // Arrange
        whenever(authModel.getUserID()).thenReturn("userID")

        // Act
        qc.fetchData(FetchType.HISTORY)

        // Assert
        verify(mainModel).getHistoryData(any(), any(), eq("userID"))
    }

    @Test
    fun `fetchData QUESTION calls getQuestionData`() = runTest {
        // Arrange

        // Act
        qc.fetchData(FetchType.QUESTION)

        // Assert
        verify(mainModel).getQuestionData()
    }

    @Test
    fun `fetchData TINDER calls getNextReview`() = runTest {
        // Arrange
        whenever(authModel.getUserID()).thenReturn("userID")

        // Act
        qc.fetchData(FetchType.TINDER)

        // Assert
        verify(mainModel).getNextReview("userID")
    }

    @Test
    fun `fetchData ANSWERED calls getUserAnswered`() = runTest {
        // Arrange

        // Act
        qc.fetchData(FetchType.ANSWERED)

        // Assert
        verify(mainModel).getUserAnswered()
    }

    @Test
    fun `handler starts and stops loading for authModel on requiresLoad true`() = runTest {
        // Arrange
        val functionMock: suspend () -> Unit = mock()
        whenever(authModel.isInit).thenReturn(true)

        // Act
        qc.handler("testFunction", requiresLoad = true, func = functionMock)

        // Assert
        inOrder(authModel) {
            verify(authModel).loading += 1
            verify(authModel).loading -= 1
        }
    }


    //HISTORY CONTROLLER
    @Test
    fun `test getUserHistoryDataByDate fetches history data correctly`() = runTest {
        // Arrange
        val from = Date(1000) // Example starting date
        val to = Date(2000) // Example end date
        val userID = "user123"
        Mockito.`when`(authModel.getUserID()).thenReturn(userID)
//        Mockito.doNothing().`when`(mainModel).invalidate(FetchType.HISTORY)
//        Mockito.doNothing().`when`(mainModel).getHistoryData(any(), any(), anyString())

        // Act
        hc.getUserHistoryDataByDate(from, to)

        // Assert
        Mockito.verify(mainModel).invalidate(FetchType.HISTORY)
        Mockito.verify(mainModel).getHistoryData(from, to, userID)
    }

    //User Controller:
    @Test
    fun `test addUserPfp calls addUserPfp in MainModel`() = runTest {
        // Arrange
        val uri = Mockito.mock(Uri::class.java)
        val context = Mockito.mock(Context::class.java)
        val userID = "user123"
        Mockito.`when`(authModel.getUserID()).thenReturn(userID)
//        Mockito.doNothing().`when`(mainModel).addUserPfp(anyString(), any(), any())

        // Act
        uc.addUserPfp(uri, context)

        // Assert
        Mockito.verify(mainModel).addUserPfp(userID, uri, context)
    }

    //Review Controller
    @Test
    fun `test verifyReview adds review correctly`() = runTest {
        // Arrange
        val reviewText = "Great explanation!"
        val clarity = 5
        val understanding = 4
        val answeredQuestion = AnsweredQuestion(questionText = "What is unit testing?", userID = "answerer123", questionID = "question123")
        val answeredQuestionID = "question123"
        val userID = "reviewer123"

        Mockito.`when`(authModel.getUserID()).thenReturn(userID)
//        Mockito.doNothing().`when`(mainModel).addReview(any())

        // Act
        rc.verifyReview(reviewText, clarity, understanding, answeredQuestion, answeredQuestionID)

        // Assert
        argumentCaptor<Review>().apply {
            Mockito.verify(mainModel).addReview(capture())
            assertEquals(reviewText, firstValue.reviewText)
            assertEquals(clarity, firstValue.clarity)
            assertEquals(understanding, firstValue.understanding)
            assertEquals(userID, firstValue.userID)
            assertEquals(answeredQuestionID, firstValue.answeredQuestionID)
            assertEquals(answeredQuestion.userID, firstValue.answeredQuestionAuthorID)
        }
    }

    @After
        fun teardown() {
            Dispatchers.resetMain()
        }
}