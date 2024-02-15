package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.User
import com.example.interviewpractice.viewmodel.Subscriber
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore

class Model: Presenter() {
    //Variables necessary to make the model work
    private val subscribers = mutableListOf<Subscriber>()
    private val systemDataSubscribers = mutableListOf<Subscriber>()
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    //----------------DATA----------------
    //systemData
    var user: FirebaseUser? = null
        set(value) {
            field = value
            notifySystemSubscribers()
        }
    //loading
    var loading: Boolean = false
        set(value) {
            field = value
            notifySystemSubscribers()
        }
    //error state
    var error: Exception? = null
        set(value) {
            field = value
            notifySystemSubscribers()
        }


    fun createAccount(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Account creation success, tell the viewModels there is a new user signed in
                    Log.d(TAG, "createUserWithEmail:success")
                    user = updateCurrentUser(username,email,password)
                    //Stop loading after we finished authentication
                    loading = false
                } else {
                    // If account creation fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    throw Exception(task.exception)
                }
            }
        //Must be non-null after successful sign-in

    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, tell the viewModels there is a new user signed in
                    Log.d(TAG, "signIn:success")
                    user = auth.currentUser
                    //Stop loading after we finished authentication
                    loading = false
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signIn:failure", task.exception)
                    throw Exception(task.exception)
                }
            }
    }

    fun logout() {
        auth.signOut()
        user = null
        //Stop loading after we finished authentication
        loading = false
    }

    private fun updateCurrentUser(username: String, email: String, password: String): FirebaseUser {
        val user = auth.currentUser
        //throw if user is null
        user ?: throw Exception("No user to update")


        //First update user profile on the user object itself.
        //This lets us have access to very common properties (like email, username, etc) just from having the user object
        val profileUpdates = userProfileChangeRequest {
            displayName=username
            photoUri=null
        }
        user.updateProfile(profileUpdates)

        val userData = User(username=username,email=email)
        db.collection("users").document(user.uid).set(userData)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "storeUserData:success")
                }
                else {
                    Log.d(TAG, "storeUserData:failure", task.exception)
                    throw Exception(task.exception)
                }
                Log.d(TAG, "User data updated")
            }
        return user
    }

    fun initAuth() {
        user = auth.currentUser
        Log.d(TAG,"$user")
        if (user != null) {
            notifySystemSubscribers()
        }
    }
    companion object {
        private const val TAG = "Model"
    }
}