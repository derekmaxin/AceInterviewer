package com.example.interviewpractice.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.interviewpractice.model.Model
import com.example.interviewpractice.viewmodel.Subscriber

class RegisterViewModel(private val model: Model): Subscriber{
    //States:
    // loading
    // success
    // Error

    fun verifyRegister(username: String, email: String, password: String) {
        try {
//            verifyUsername(username)
//            verifyPassword(password)
//            verifyEmail(email)
//
//            //After this point, we have no user errors
//            model.createUser()
//            model.
//
//            firebase.write("users","")
//
//            firebase.createuserwithemailandpassword



        }
        catch (ex: Exception) {
            //Catch the errors here
            //Just deal with user errors for now
        }
        //We have no errors from here
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

    override fun update() {
        Log.d("LOGIN VIEW MODEL", "Updated")
    }

}