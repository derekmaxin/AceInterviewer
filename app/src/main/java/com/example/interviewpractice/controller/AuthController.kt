package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.helpers.EH
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AuthController(private val model: AuthModel) {
    fun verifyRegister(username: String, password:String,email:String) {
        handler("verifyRegister") {
            model.loading = true
//            verifyUsername(username)
//            verifyPassword(password)
//            verifyEmailFormat(email)

            model.createAccount(username = username.trim(), email = email.trim(), password = password.trim())
            //User should now be authenticated, and data stored in the Firestore

            //Stop loading after we finished authentication
            model.loading = false
            Log.d(TAG,"verifyRegister:success")
        }
    }
    fun verifySignIn(password:String,email:String) {
        handler("verifySignIn") {
            model.loading = true
            verifyPasswordFormat(password)
            verifyEmailFormat(email)
            model.signIn(email = email, password = password.trim())
            //User should now be authenticated
//
            //Stop loading after we finished authentication
            model.loading = false
            Log.d(TAG,"verifySignIn:success")
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
//            verifyEmailFormat(email)

            model.resetPassword(email)
        }
    }

    private fun handler(functionName: String, func: suspend () -> Unit) {
        MainScope().launch {
            try {
                func()
            }
            catch (ex: FirebaseAuthException) {
                Log.d(TAG,"$functionName: failed with error code ${ex.errorCode}")
                Log.w(TAG,"$functionName: -> ${ex.message}")
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
        }
    }

    private fun verifyUsernameFormat(username: String) {
        verifyGenericString(username, "username")
    }
    private fun verifyEmailFormat(email: String) {
        verifyGenericString(email, "email")
    }
    private fun verifyPasswordFormat(password: String) {
        verifyGenericString(password, "password")
    }

    private fun verifyGenericString(str: String, field: String) {
        if (str.isEmpty()) {
            throw UserException("$field field is empty")
        }

    }

    companion object {
        private const val TAG = "UserController"
    }
}