package com.example.interviewpractice.controller

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import android.util.Patterns

class UserController(private val model: Model) {
    fun verifyRegister(username: String, password:String,email:String) {
        handler("verifyRegister") {
            model.loading = true
            verifyUsername(username)
            verifyPassword(password)
            verifyEmailFormat(email)

            model.createAccount(username = username.trim(), email = email.trim(), password = password.trim())
        }
    }
    fun verifySignIn(password:String,email:String) {
        handler("verifySignIn") {
            model.loading = true
            verifyPassword(password)
            verifyEmailFormat(email)

            model.signIn(email = email, password = password.trim())
        }
    }

    fun verifyLogout() {
        handler("verifyLogout") {
            model.loading = true

            model.logout()
        }
    }

    fun verifyForgotPassword(email: String) {
        handler("verifyForgotPassword") {
            verifyEmailFormat(email)

            model.resetPassword(email)
        }
    }

    private fun handler(functionName: String, func: () -> Unit) {
        try {
            func()
        }
        catch(ex: UserException) {
            //Catch user errors
            Log.w(TAG, "$functionName:userException -> ${ex.message}")
            model.error = UIError(ex.message!!,ErrorType.USER)

            //Cleanup
            model.loading = false
        }
        catch(ex: Exception) {
            //Catch system errors
            //The model will do its own error checking, so this block should only be reached if something goes very wrong
            Log.wtf(TAG, "$functionName:catastrophicFailure", ex)
//            ex.printStackTrace()
            model.error = UIError(ex.toString(),ErrorType.CATASTROPHIC)

            //Cleanup
            model.loading = false

        }
        //Todo: deal with exceptions on the frontend.
        //I suggest using "snackbars" https://m2.material.io/components/snackbars/android#using-snackbars
    }
    private fun verifyEmailFormat(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) throw UserException("Improperly formatted email")
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