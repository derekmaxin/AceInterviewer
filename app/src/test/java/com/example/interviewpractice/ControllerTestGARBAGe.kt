package com.example.interviewpractice

import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UserException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import org.junit.Before
import org.junit.Test
import org.junit.After
import org.mockito.Mockito
import java.util.Calendar
import java.util.Date
import kotlin.Exception

class AuthControllerTest {
    lateinit var authmodel: AuthModel
    lateinit var mainmodel: MainModel
    lateinit var authcontroller: AuthController

    private lateinit var authModel: AuthModel
    private val mockAuth: FirebaseAuth = Mockito.mock(FirebaseAuth::class.java)
    private val mockDb: FirebaseFirestore = Mockito.mock(FirebaseFirestore::class.java)
    private val mockStorage: FirebaseStorage = Mockito.mock(FirebaseStorage::class.java)

    val validDate = Date(100, 11, 23)
    val validTags = setOf(Tag.BUSINESS, Tag.PHYSICS, Tag.ENGLISH)

    @Before
    fun setup() {
        authModel = AuthModel(mockAuth,mockDb)
        mainmodel = MainModel(mockAuth,mockDb,mockStorage)
        authcontroller = AuthController(mainmodel,authModel)

    }

    @Test
    fun usernameBlank() {
        //arrange
        val username = ""
        var isCorrect : Boolean

        //act
        try {
            authcontroller.verifyUsernameFormat(username)
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
            authcontroller.verifyUsernameFormat(username)
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
            authcontroller.verifyPasswordFormat(text)
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
            authcontroller.verifyPasswordFormat(text)
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
            authcontroller.verifyConfirm(password, confirm)
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
            authcontroller.verifyEmailFormat(email)
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
            authcontroller.verifyEmailFormat(email)
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
            authcontroller.verifyBirthday(date)
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
            authcontroller.verifyFOI(emptySet())
            isCorrect = false
        } catch (e: Exception) {
            isCorrect = true
        }

        //assert
        assert(isCorrect)
    }

    @After
    fun teardown() {

    }
}