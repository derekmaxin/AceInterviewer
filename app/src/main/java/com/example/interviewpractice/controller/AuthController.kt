package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.helpers.EH
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AuthController(private val model: AuthModel) {
    fun verifyRegister(username: String, password:String,email:String) {
        handler("verifyRegister") {
            verifyUsernameFormat(username)
            verifyPasswordFormat(password)
            verifyEmailFormat(email)

            model.createAccount(username = username, email = email, password = password)
            //User should now be authenticated, and data stored in the Firestore

            Log.d(TAG,"verifyRegister:success")
        }
    }
    fun verifySignIn(password:String,email:String) {
        handler("verifySignIn") {
            verifyPasswordFormat(password)
            verifyEmailFormat(email)
            model.signIn(email = email, password = password)
            //User should now be authenticated
            Log.d(TAG,"verifySignIn:success")
            }

    }

    fun verifyLogout() {
        handler("verifyLogout") {
            model.logout()
        }
    }

    fun verifyForgotPassword(email: String) {
        handler("verifyForgotPassword") {
            verifyEmailFormat(email)

            model.resetPassword(email)
            Log.d(TAG,"verifyForgotPassword:success (sent email)")
        }
    }

    private fun handler(functionName: String, func: suspend () -> Unit) {
        MainScope().launch {
            try {
                model.loading = true
                func()
            }
            catch (ex: FirebaseAuthException) {
                //Represents user errors that are caught by Firebase Auth
                model.error = UIError(ex.message!!,ErrorType.USER)
                Log.w(TAG,"$functionName:userException[${ex.errorCode}] --> ${ex.message}")

            }
            catch (ex: FirebaseFirestoreException) {
                //Represents user errors that are caught by Firestore
                model.error = UIError(ex.message!!,ErrorType.USER)
                Log.w(TAG,"$functionName:userException --> ${ex.message}")
            }
            catch (ex: FirebaseException) {
                //Represents all remaining (system) errors that are caught by Firebase
                model.error = UIError(ex.message!!,ErrorType.SYSTEM)
                Log.e(TAG,"$functionName:systemException --> ${ex.message}")
            }
            catch(ex: UserException) {
                //Represents all user errors that are caught by us
                model.error = UIError(ex.message!!,ErrorType.USER)
                Log.w(TAG, "$functionName:userException -> ${ex.message}")
            }
            catch(ex: Exception) {
                //Represents all remaining errors that weren't caught by Firebase or us
                //If we reach here, something very bad has happened
                model.error = UIError(ex.toString(),ErrorType.CATASTROPHIC)
                Log.wtf(TAG, "$functionName:catastrophicFailure", ex)

            }
            finally {
                //Stop loading after we finished authentication
                model.loading = false
            }
        }
    }

    private fun verifyUsernameFormat(username: String) {
        //Add more checks here as necessary
        verifyGenericString(username, "Username")
    }
    private fun verifyEmailFormat(email: String) {
        //Add more checks here as necessary
        verifyGenericString(email, "Email")
    }
    private fun verifyPasswordFormat(password: String) {
        //Add more checks here as necessary
        verifyGenericString(password, "Password")
    }

    private fun verifyGenericString(str: String, field: String) {
        if (str.isEmpty()) {
            throw UserException("$field field is empty")
        }
    }

    companion object {
        private const val TAG = "AuthController"
    }
}