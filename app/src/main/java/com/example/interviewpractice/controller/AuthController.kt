package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.helpers.EH
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Tag
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


class AuthController(mm: MainModel, am: AuthModel): Controller(mm,am,TAG) {

//    override fun fetchData(ft: FetchType) {
//        am.refresh()
//    }
    fun verifyRegister(username: String, password:String,email:String, foi: Set<Tag>,
                       ) {
        handler("verifyRegister",true) {
            verifyUsernameFormat(username)
            verifyPasswordFormat(password)
            verifyEmailFormat(email)

            am.createAccount(username = username, email = email, password = password, foi=foi)
            //User should now be authenticated, and data stored in the Firestore

            Log.d(TAG,"verifyRegister:success")
        }
    }
    fun verifySignIn(password:String,email:String) {
        Log.d(TAG,"SIGNING IN AS: $email")
        handler("verifySignIn",true) {
            verifyPasswordFormat(password)
            verifyEmailFormat(email)
            am.signIn(email = email, password = password)
            //User should now be authenticated
            Log.d(TAG,"verifySignIn:success")
            }

    }

    fun verifyLogout() {
        handler("verifyLogout") {
            am.logout()
            mm.reset()
        }
    }

    fun verifyForgotPassword(email: String) {
        handler("verifyForgotPassword",true) {
            verifyEmailFormat(email)

            am.resetPassword(email)
            Log.d(TAG, "verifyForgotPassword:success (sent email)")
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