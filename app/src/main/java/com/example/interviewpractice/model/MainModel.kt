package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class MainModel: Presenter() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    val reviewScores: MutableList<Pair<String, Int>> = mutableListOf()

    fun updateReviewScore(index: Int, score: Int) {
        reviewScores[index] = Pair(reviewScores[index].first, score)
        notifySubscribers()
    }

    var searchResults = mutableListOf<Question>()

    var user: User? = null


    suspend fun addQuestion(question: Question) {
        db.collection("questions").add(question).await()
        Log.d(TAG,"addQuestion:success")
    }

    suspend fun searchQuestion(queryText: String) {
        val questionsRef = db.collection("questions")

        val query = questionsRef
            .whereGreaterThanOrEqualTo("questionText", queryText)
            .whereLessThanOrEqualTo("questionText", queryText + "\uf8ff")
            .get().await()
        searchResults.clear()
        for (question in query) {
            Log.d(TAG, "QUERY ${question.id} => ${question.data}")
            searchResults.add(question.toObject(Question::class.java))
        }
        notifySubscribers()
    }

    suspend fun getCurrentUserData() {
        Log.d(TAG,"USER FOUND: ${user}")
        user?.let {
            Log.d(TAG,"Found saved value")
            notifySubscribers()
            return
        } ?: run {
            val uid = auth.currentUser?.uid
            uid?.let {
                val userDoc = db.collection("users").document(uid)
                val query = userDoc.get().await()
//                delay(1500)
                user = query.toObject(User::class.java)

                Log.d(TAG,"USER SET: $user")
            }
        }

    }


    companion object {
        private const val TAG = "MainModel"
    }
}