package com.example.interviewpractice

import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UserException

import org.junit.Before
import org.junit.Test
import org.junit.After
import org.junit.Ignore
import org.junit.runner.RunWith

import java.lang.Exception
import java.util.Calendar
import java.util.Date

class AuthControllerTest {
    lateinit var authmodel: AuthModel
    lateinit var mainmodel: MainModel
    lateinit var authcontroller: AuthController

    val validDate = Date(100, 11, 23)
    val validTags = setOf(Tag.BUSINESS, Tag.PHYSICS, Tag.ENGLISH)

    @Before
    fun setup() {
        authmodel = AuthModel()
        mainmodel = MainModel()
        authcontroller= AuthController(mainmodel, authmodel)
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

    @Ignore
    @Test(expected = UserException::class)
    fun passwordTooShort() {
        authcontroller.verifyPasswordFormat("1")
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
        authcontroller.verifyEmailFormat("")
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