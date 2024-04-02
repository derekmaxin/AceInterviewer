package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.User
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.SystemException
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class AuthModel: Presenter() {
    //Variables necessary to make the model work
    //Subscribers here will ONLY be the xx
    private val subscribers = mutableListOf<Subscriber>()
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    //----------------DATA----------------
    //systemData
    var user: FirebaseUser? = null
        set(value) {
            field = value
            notifySubscribers()
        }
    //loading
    var loading: Boolean = false
        set(value) {
            field = value
            notifySubscribers()
        }
    //error state
    var error: UIError? = null
        set(value) {
            field = value
            notifySubscribers()
        }

    fun clearError() {
        error = null
    }

    fun refresh() {
        notifySubscribers()
    }
    suspend fun createAccount(
        username: String, email: String, password: String, foi: Set<Tag>, birthday: Date
    ){
        auth.createUserWithEmailAndPassword(email, password).await()
        updateCurrentUser(email = email, username = username, foi=foi, birthday = birthday)
        Log.d(TAG, "createAccount:success")
    }

    suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        user = auth.currentUser
        Log.d(TAG, "signIn:success")
    }

    fun logout() {
        auth.signOut()
        user = null
        notifySubscribers()

        //Stop loading after we finished authentication
    }

    suspend fun resetPassword(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
        Log.d(TAG, "resetPassword:success (sent email)")
    }

    private suspend fun updateCurrentUser(
        username: String, email: String, foi:Set<Tag>, birthday : Date
    ) {
        val current_user = auth.currentUser
        //throw if user is null
        current_user ?: throw CatastrophicException("User is not persistent")

        //First update user profile on the user object itself.
        //This lets us have access to very common properties (like email, username, etc) just from having the user object
        val profileUpdates = userProfileChangeRequest {
            displayName=username
            photoUri=null
        }
        current_user.updateProfile(profileUpdates)

        val userData = User(
            username =username,
            email =email, fieldsOfInterest = foi.toList(), birthday =birthday)
        setUserData(userData,current_user.uid)

        user = current_user
        Log.d(TAG,"notifySystemSubscribers should have just been called")
        Log.d(TAG,"updateCurrentUser:success")
    }

    private suspend fun setUserData(userData: User, uid: String) {
        db.collection("users").document(uid).set(userData).await()
        Log.d(TAG,"setUserData:success")
    }

    fun initAuth() {
        user = auth.currentUser
        Log.d(TAG,"$user")
        if (user != null) {
            notifySubscribers()
        }
    }

    fun getUserID(): String {
        user = auth.currentUser
        val currentUser = user
        if (currentUser != null) {
            return currentUser.uid
        }
        throw SystemException("No user found")
    }

    companion object {
        private const val TAG = "AuthModel"
    }
}