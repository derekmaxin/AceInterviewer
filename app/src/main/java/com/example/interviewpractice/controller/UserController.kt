package com.example.interviewpractice.controller

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.Model

class UserController(private val model: Model) {
    fun verifyRegister(username: String, password:String,email:String) {
        try {
            model.loading = true
            verifyUsername(username)
            verifyPassword(password)
            verifyEmail(email)

            //After this point, we have no user errors

            model.createAccount(username = username.trim(), email = email.trim(), password = password.trim())
        }
        catch (ex: Exception) {
            Log.w(TAG, "verifyRegister:failure", ex)
            model.error = ex
            model.loading = false
            //Todo: deal with exceptions. I suggest using "snackbars" https://m2.material.io/components/snackbars/android#using-snackbars

        }
    }
    fun verifySignIn(password:String,email:String) {
        try {
            model.loading = true
//            Thread.sleep(4000)
            verifyPassword(password)
            verifyEmail(email)
            //After this point, we have no user errors

            model.signIn(email = email.trim(), password = password.trim())
        }
        catch (ex: Exception) {
            Log.w(TAG, "verifySignIn:failure", ex)
            model.error = ex
            model.loading = false
            //Todo: deal with exceptions. I suggest using "snackbars" https://m2.material.io/components/snackbars/android#using-snackbars
        }
    }

    fun verifyLogout() {
        try {
            model.loading = true

            //After this point, we have no user errors

            model.logout()
        }
        catch (ex: Exception) {
            Log.w(TAG, "verifyLogout:failure", ex)
            model.error = ex
            model.loading = false
            //Todo: deal with exceptions. I suggest using "snackbars" https://m2.material.io/components/snackbars/android#using-snackbars
        }
    }
    private fun verifyEmail(email: String) {
        return
    }
    private fun verifyPassword(password: String) {
        return
    }
    private fun verifyUsername(username: String) {
        return
    }
    companion object {
        private const val TAG = "UserController"
    }
}