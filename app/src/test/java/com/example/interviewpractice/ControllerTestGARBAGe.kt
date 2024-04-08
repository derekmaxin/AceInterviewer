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

import java.util.Date

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

    @Test(expected = UserException::class)
    fun usernameBlank() {
        authcontroller.verifyUsernameFormat("")
    }

    @Test
    fun usernamePerfect() {
        authcontroller.verifyUsernameFormat("testuser1")
    }

    @Test(expected = UserException::class)
    fun passwordBlank() {
        authcontroller.verifyPasswordFormat("")
    }
    @Test
    fun passwordPerfect() {
        authcontroller.verifyPasswordFormat("pass06")
    }

    @Test(expected = UserException::class)
    fun passwordsDoNotMatch() {
        authcontroller.verifyConfirm("123456", "654321")
    }

    @Test(expected = UserException::class)
    fun emailBlank() {
        authcontroller.verifyEmailFormat("")
    }

    @Test
    fun emailPerfect() {
        authcontroller.verifyEmailFormat("ryan@gmail.com")
    }

    @Test(expected = UserException::class)
    fun birthdayInvalidUnder16() {
        authcontroller.verifyBirthday(Date())
    }

    @Test(expected = UserException::class)
    fun noInterestsSelected() {
        authcontroller.verifyFOI(emptySet())
    }

    @After
    fun teardown() {

    }
}