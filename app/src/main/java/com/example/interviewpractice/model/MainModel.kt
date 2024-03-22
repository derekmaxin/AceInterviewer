package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class MainModel: Presenter() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    var localLoading: Boolean = false
        set(value) {
            field = value
            notifySubscribers()
        }

//    val reviewScores: MutableList<Pair<String, Int>> = mutableListOf()
//
//    fun updateReviewScore(index: Int, score: Int) {
//        reviewScores[index] = Pair(reviewScores[index].first, score)
//        notifySubscribers()
//    }

    var searchResults = mutableListOf<Question>()
    var leaderBoardStandings = mutableListOf<User>()

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
            searchResults.add(question.toObject<Question>())
        }
        notifySubscribers()
    }

    //------------[FETCH METHODS]------------
    suspend fun getCurrentUserData() {
        if (noCache(FetchType.PROFILE)) {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val userDoc = db.collection("users").document(uid)
                val query = userDoc.get().await()

                user = query.toObject<User>()

                notifySubscribers()
            }
            else {
                throw CatastrophicException("No uid for signed in user")
            }
        }
        notifySubscribers()
    }

    suspend fun getLeaderBoardData() {
        if (noCache(FetchType.LEADERBOARD)) {
            val usersRef = db.collection("users")
            val query = usersRef.orderBy("questionsAnswered").limit(10).get().await()
            leaderBoardStandings.clear()
            for (user in query) {
                Log.d(TAG, "QUERY ${user.id} => ${user.data}")
                leaderBoardStandings.add(user.toObject<User>())
            }

            val emptyUser = User(username="None", questionsAnswered = 0)

            while(leaderBoardStandings.size < 10) {
                leaderBoardStandings.add(emptyUser)
            }
        }
        notifySubscribers()

    }
    fun refresh() {
        notifySubscribers()
    }

    fun noCache(ft: FetchType): Boolean {
        return when (ft) {
            FetchType.PROFILE-> (user == null)
            FetchType.LEADERBOARD-> (leaderBoardStandings.isEmpty())
            FetchType.SEARCH-> false
        }
    }

    companion object {
        private const val TAG = "MainModel"
    }
}