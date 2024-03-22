package com.example.interviewpractice.model

import android.util.Log
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
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
    var homePageRecommendations: Question? = null

    var user: User? = null
    suspend fun addQuestion(question: Question) {
        db.collection("questions").add(question).await()
        Log.d(TAG,"addQuestion:success")
    }

    suspend fun searchQuestion(queryText: String,filters: Set<Tag> = emptySet(),self:Boolean=false) {
        val questionsRef = db.collection("questions")
        var filty = filters.toList()

        if (self) {
            var currUser = user
            if (currUser != null) {
                filty = currUser.fieldsOfInterest
            }
            else {
                getCurrentUserData()
                currUser = user
                if (currUser != null) {
                    filty = currUser.fieldsOfInterest
                }
            }
        }
        Log.d(TAG,"THESE ARE THE USER TAGS: $filty, ${filty.isEmpty()}, qtext: $queryText")
        var queryss = questionsRef
            .whereGreaterThanOrEqualTo("questionText", queryText)
            .whereLessThanOrEqualTo("questionText", queryText + "\uf8ff")

        if (filty.isNotEmpty()) {
            Log.d(TAG,"REASSIGNED")
            queryss = questionsRef
                .whereGreaterThanOrEqualTo("questionText", queryText)
                .whereLessThanOrEqualTo("questionText", queryText + "\uf8ff")
                .whereArrayContainsAny("tags", filty )
        }
        val query = queryss.get().await()


        searchResults.clear()
        for (question in query) {
            Log.d(TAG, "QUERY ${question.id} => ${question.data}")
            searchResults.add(question.toObject<Question>())
        }
        if (self) {
            if (searchResults.isNotEmpty()) {
                homePageRecommendations = searchResults[0]
            }
            else {
                homePageRecommendations = Question("We have no questions in your field of interest!",
                    emptyList(),false,false,false,"")
            }

        }
        notifySubscribers()
    }

    //------------[TEST METHODS]------------
    suspend fun boost() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = db.collection("users").document(uid)
            // Atomically increment the population of the city by 50.
            userRef.update("questionsAnswered", FieldValue.increment(1))

            leaderBoardStandings.clear()
            notifySubscribers()
        }
        else {
            throw CatastrophicException("No uid for signed in user")
        }
    }

    //------------[FETCH METHODS]------------
    suspend fun getCurrentUserData() {
        if (noCache(FetchType.PROFILE)) {
            val uid = auth.currentUser?.uid
            Log.d(TAG,"APPARENT USER: ${auth.currentUser?.email}")
            if (uid != null) {
                val userDoc = db.collection("users").document(uid)
                val query = userDoc.get().await()

                user = query.toObject<User>()
                Log.d(TAG,"APPARENT CURRENT USER: ${user.toString()}")
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
            val query = usersRef.orderBy("questionsAnswered", Query.Direction.DESCENDING).limit(10).get().await()
            leaderBoardStandings.clear()
            for (user in query) {
                Log.d(TAG, "QUERY ${user.id} => ${user.data}")
                leaderBoardStandings.add(user.toObject<User>())
            }
            Log.d(TAG,"TOP QUERY: $leaderBoardStandings")

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
    fun reset() {
        user = null
        searchResults.clear()
        leaderBoardStandings.clear()
        homePageRecommendations = null
        notifySubscribers()
    }

    fun noCache(ft: FetchType): Boolean {
        return when (ft) {
            FetchType.PROFILE-> (user == null)
            FetchType.LEADERBOARD-> (leaderBoardStandings.isEmpty())
            FetchType.SEARCH-> false
            FetchType.RECOMMENDATION->false
            FetchType.RESETUSER->true
        }
    }

    companion object {
        private const val TAG = "MainModel"
    }
}