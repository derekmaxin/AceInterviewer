package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.Question
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
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


    companion object {
        private const val TAG = "MainModel"
    }
}