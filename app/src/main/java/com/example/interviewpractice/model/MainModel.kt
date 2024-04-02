package com.example.interviewpractice.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.Collections
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Notification
import com.example.interviewpractice.types.NotificationType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.InputStream
import java.util.UUID

class MainModel() : Presenter() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

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

    var newReviewNotifications = mutableListOf<Notification>()
    var notificationCount = 0

    var user: User? = null

    //USER FUNCTIONS
    suspend fun addUserPfp(userID: String, uri: Uri, context: Context){
        val userRef = db.collection("users").document(userID)
        val storageRef = storage.reference.child("profile_pictures")
        val imageName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$userID/$imageName")

        // Open an input stream from the content URI
        val inputStream = context.contentResolver?.openInputStream(uri)

        // Upload file to Firebase Storage
        inputStream?.let { stream : InputStream ->
            val uploadTask = imageRef.putStream(stream)

            // Register observers to listen for upload success or failure
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                // Get the download URL of the image
                imageRef.downloadUrl.addOnSuccessListener { downloadUri : Uri ->
                    // Store the download URL in Firestore or Realtime Database
                    userRef.update("pfpURL", downloadUri.toString())
                }.addOnFailureListener { exception ->
                    Log.e(TAG, "Failed to get download URL: $exception")
                    // Handle any errors while getting download URL
                }
            }.addOnFailureListener { exception ->
                // Handle unsuccessful uploads
                Log.e(TAG, "Failed to upload image: $exception")
            }
        } ?: run {
            Log.e(TAG, "Failed to open input stream for URI: $uri")
        }
    }


    suspend fun addQuestion(question: Question) {
        db.collection("questions").add(question).await()
        Log.d(TAG,"addQuestion:success")
    }

    suspend fun addReview(review: Review) {
        //Add review itself
        db.collection("reviews").add(review).await()
        Log.d(TAG,"addReview::success")


        //Add notification
        val notification = Notification(
            notificationText = "A new review was added for question ${review.answeredQuestionID}",
            type = NotificationType.NEWREVIEW,
            review.answeredQuestionID,
            review.answeredQuestionAuthorID)
        db.collection("notifications").add(notification).await()
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

    fun addNotificationListener(currentUserID: String) {
        //ASSUME ONLY NEW REVIEW NOTIFICATIONS (FOR NOW FOR SIMPLICITY)
        val docRef = db.collection(Collections.notifications.toString()).whereEqualTo("userID",currentUserID)

        docRef.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        //New review came in
                        val review = dc.document.toObject<Review>()
                        val notification = Notification(
                            notificationText = "A new review was added for question ${review.answeredQuestionID}",
                            type = NotificationType.NEWREVIEW,
                            review.answeredQuestionID,
                            currentUserID)
                        newReviewNotifications.add(notification)
                        if (notificationCount == -1) {
                            notificationCount += 0
                        }
                        notificationCount += 1

                        notifySubscribers()
                        Log.d(TAG, "New Notification: ${dc.document.data}")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        //MIGHT NEED TO CHANGE
                        Log.d(TAG, "Notification changed: ${dc.document.data}")
                    }
                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Notification read: ${dc.document.data}")
                }
            }
        }
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

            val emptyUser = User(username ="None", questionsAnswered = 0)

            while(leaderBoardStandings.size < 10) {
                leaderBoardStandings.add(emptyUser)
            }
        }
        notifySubscribers()

    }
    suspend fun getNotificationData() {
        if (noCache(FetchType.NOTIFICATION)) {
            val notificationRef = db.collection("notifications")
            val query = notificationRef.get().await()
            for (notification in query) {
                //ASSUME ONLY NEW REVIEW NOTIFICATIONS FOR NOW
                newReviewNotifications.add(notification.toObject<Notification>())

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
        notificationCount = 0
        newReviewNotifications.clear()
        notifySubscribers()
    }

    fun noCache(ft: FetchType): Boolean {
        return when (ft) {
            FetchType.PROFILE-> (user == null)
            FetchType.LEADERBOARD-> (leaderBoardStandings.isEmpty())
            FetchType.SEARCH-> false
            FetchType.RECOMMENDATION->false
            FetchType.RESETUSER->true
            FetchType.NOTIFICATION->false
        }
    }
    val userQuestions = mutableListOf<Question>()

    suspend fun searchUserQuestion() {
        val questionsRef = db.collection("questions")

        val query = questionsRef.whereEqualTo("userID", auth.currentUser?.uid)
            .get()
            .await()
        userQuestions.clear()
        for (question in query) {
            Log.d(TAG, "QUERY ${question.id} => ${question.data}")
            userQuestions.add(question.toObject(Question::class.java))
        }
        notifySubscribers()
    }

    suspend fun uploadAudio(audioFile: File, context: Context){
        val storageRef = storage.reference.child("audio_recordings/${audioFile.name}")

        val fileUri = FileProvider.getUriForFile(context, context.packageName + ".provider", audioFile)
        val uploadTask = storageRef.putFile(fileUri)
        uploadTask.addOnSuccessListener {
            // Audio uploaded successfully
            // You can get the download URL here and save it to Firestore or Realtime Database
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                // Save downloadUrl to Firestore or Realtime Database
                Log.d("AUDIO URL", "$downloadUrl")
            }
        }.addOnFailureListener {
            // Handle failure
        }
    }

    companion object {
        private const val TAG = "MainModel"
    }
}