package com.example.interviewpractice.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.interviewpractice.helpers.getCurrentDate
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.CatastrophicException
import com.example.interviewpractice.types.Collections
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.History
import com.example.interviewpractice.types.Notification
import com.example.interviewpractice.types.NotificationType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.AggregateField
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.Date
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainModel() : Presenter() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    var localLoading: Boolean = false
        set(value) {
            field = value
            Log.d(TAG, "Updated LOCAL loading state: $localLoading")
            notifySubscribers()
        }

//    val reviewScores: MutableList<Pair<String, Int>> = mutableListOf()
//
//    fun updateReviewScore(index: Int, score: Int) {
//        reviewScores[index] = Pair(reviewScores[index].first, score)
//        notifySubscribers()
//    }

    var isCached = mutableMapOf<FetchType,Boolean>()

    fun invalidateAll() {
        FetchType.entries.forEach {
            isCached[it] = false
        }
    }

    fun invalidate(ft: FetchType) {
        if (ft == FetchType.QUESTION) {
            isCached[ft] = true
        }
        else {
            isCached[ft] = false
        }

    }

    fun check(ft: FetchType): Boolean {
        return isCached.getValue(ft)
    }

    //FetchType:

    //SEARCH
    var searchResults = mutableListOf<Question>()

    //LEADERBOARD
    var leaderBoardStandings = mutableListOf<User>()

    //RECOMMENDATION
    var homePageRecommendations: Question? = null

    //HISTORY
    var historyHeatData = mutableMapOf<Date,Int>()
    var historyChartData = mutableListOf<History>()

    //NOTIFICATION
    var newReviewNotifications = mutableListOf<Notification>()
    var notificationCount = 0

    //PROFILE
    var user: User? = null

    //QUESTION
    var currentQuestionData: Question? = null

    //TINDER
    var currentReviewData = mutableListOf<AnsweredQuestion>()
    var currentIteration: Int = 1;

    //
    var userQuestions = mutableListOf<Question>()
    var userAnswered = mutableListOf<AnsweredQuestion>()

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

    suspend fun addAnsweredQuestion(question: AnsweredQuestion, fileUri: String) {
        val ref: DocumentReference = db.collection("answered").document()
        val myId = ref.id
        //val document = db.collection("answered").add(question).await()
        if (fileUri.isNotEmpty()) {
            val downloadUrl = uploadAudio(fileUri, myId)
            Log.d("downloadUrl", "$downloadUrl")
            question.downloadUrl = downloadUrl
        }
        db.collection("answered").add(question).await()
        db.collection("users").document(question.userID)
            .update("questionsAnswered", FieldValue.increment(1)).await()
        invalidate(FetchType.HISTORY)
        Log.d(TAG, "addAnsweredQuestion:success")
    }

    suspend fun uploadAudio(audioURI: String, aqid: String): String {
        return suspendCoroutine { continuation ->
            val storageRef = storage.reference.child("audio_recordings/$aqid")
            val uploadTask = storageRef.putFile(Uri.parse(audioURI))
            uploadTask.addOnSuccessListener { _ ->
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    continuation.resume(downloadUrl)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        }
    }

    suspend fun addReview(review: Review) {
        //Add review itself
        val document = db.collection("reviews").add(review).await()
        //TODO: invalidate reviews


        //Add notification
        val notification = Notification(
            notificationText = "A new review was added for question ${review.answeredQuestionID}",
            type = NotificationType.NEWREVIEW,
            review.answeredQuestionID,
            review.answeredQuestionAuthorID,
            document.id)
        db.collection("notifications").add(notification).await()

        db.collection("answered").document(review.answeredQuestionID).update("reviewCount", FieldValue.increment(1))
        invalidate(FetchType.NOTIFICATION)
        invalidate(FetchType.HISTORY)
        Log.d(TAG,"addReview::success")
    }

    suspend fun searchQuestion(queryText: String,filters: List<Tag> = emptyList(),self:Boolean=false) {
        val questionsRef = db.collection("questions")
        var filty = filters

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
                    emptyList(),false,false,false,"", getCurrentDate(), emptyList()
                )
            }

        }
        isCached[FetchType.SEARCH] = true
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


                        Log.d(TAG, "New Notification: ${dc.document.data}")
                    }
                    DocumentChange.Type.MODIFIED -> {
                        //MIGHT NEED TO CHANGE
                        Log.d(TAG, "Notification changed: ${dc.document.data}")
                    }
                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Notification removed: ${dc.document.data}")
                }
            }
            notifySubscribers()
            invalidate(FetchType.NOTIFICATION)
        }
    }

    suspend fun searchUserAnswered() {
        val questionsRef = db.collection("answered")

        val query = questionsRef.whereEqualTo("userID", auth.currentUser?.uid)
            .get()
            .await()
        userAnswered.clear()
        for (question in query) {
            Log.d(TAG, "QUERY ${question.id} => ${question.data}")
            userAnswered.add(question.toObject(AnsweredQuestion::class.java))
        }
        notifySubscribers()
    }

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

    //------------[TEST METHODS]------------
    suspend fun boost() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            val userRef = db.collection("users").document(uid)
            // Atomically increment the population of the city by 50.
            userRef.update("questionsAnswered", FieldValue.increment(1))

            leaderBoardStandings.clear()
            notifySubscribers()
            invalidate(FetchType.LEADERBOARD)
        }
        else {
            throw CatastrophicException("No uid for signed in user")
        }
    }



    //------------[FETCH METHODS]------------
    suspend fun getCurrentUserData() {
        fetch(FetchType.PROFILE) {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val userDoc = db.collection("users").document(uid)
                val query = userDoc.get().await()

                user = query.toObject<User>()
            }
            else {
                throw CatastrophicException("No uid for signed in user")
            }
        }
    }
    suspend fun getQuestionData() {
        fetch(FetchType.QUESTION) {}
    }

    suspend fun getLeaderboardData() {
        fetch(FetchType.LEADERBOARD) {
            val usersRef = db.collection("users")
            val query = usersRef.orderBy("questionsAnswered", Query.Direction.DESCENDING).limit(10).get().await()
            leaderBoardStandings.clear()
            for (user in query) {
                leaderBoardStandings.add(user.toObject<User>())
            }

            //If there are not enough users
            val emptyUser = User(username ="None", questionsAnswered = 0)

            while(leaderBoardStandings.size < 10) {
                leaderBoardStandings.add(emptyUser)
            }
        }
    }
    suspend fun getNotificationData(currentUser: String) {
        fetch(FetchType.NOTIFICATION) {
            val notificationRef = db.collection("notifications").whereEqualTo("userID",currentUser)
            val query = notificationRef.get().await()
            newReviewNotifications.clear()
            notificationCount = 0
            for (notification in query) {
                //ASSUME ONLY NEW REVIEW NOTIFICATIONS FOR NOW
                newReviewNotifications.add(notification.toObject<Notification>())
                notificationCount+= 1
            }
        }
    }
    suspend fun getNextReview(currentUser: String) {
        fetch(FetchType.TINDER) {
            var requiredReviews = 1
            var requiredProficency = 90
            when (currentIteration) {
                1 -> {
                    requiredReviews = 1
                }
                2 -> {
                    requiredReviews = 3
                }
                3 -> {
                    requiredReviews = 5
                }
                4 -> {
                    requiredReviews = 7
                }
                5 -> {
                    requiredReviews = 9
                }
                else -> {
                    requiredReviews = currentIteration*2 - 1
                }
            }

            getCurrentUserData()

            if (user ==null) throw CatastrophicException("No user data found")
            val foi = user!!.fieldsOfInterest

            val questionRef = db.collection("answered")
                .whereLessThanOrEqualTo("reviewCount", requiredReviews )
//                .orderBy("date",Query.Direction.ASCENDING)

            val query = questionRef.get().await()

            for (entry in query ) {
                val answered = entry.toObject<AnsweredQuestion>()
                if (answered.tags.any { it in foi }) {
                    currentReviewData.add(answered)
                }
            }

            /*



                    Iteration 1 Get the oldest 10 questions answered in which the user has proficiency in all tags >90 and have 0 to 1 reviews
                    Iteration 2 Get the oldest 10 questions answered in which the user has proficiency in all tags >85 and have 0 to 3 reviews
                    Iteration 3 Get the oldest 10 questions answered in which the user has proficiency in all tags >80 and have 0 to 5 reviews
                    Iteration 4 Get the oldest 10 questions answered in which the user has proficiency in all tags >75 and have 0 to 7 reviews
                    Iteration 5 Get the oldest 10 questions answered in which the user has proficiency in all tags >70 and have 0 to 9 reviews

                    Each iteration after this point should not decrease it's minimum proficiency requirement, but should increase its max number of reviews by 2 each iteration.
                    The results should then be displayed in the order that they were collected (matches found in Iteration 1 come before Iteration 2, for example).
            */
        }
    }
    suspend fun getHistoryData(from: Date, to: Date, currentUser: String) {
        fetch(FetchType.HISTORY) {
            Log.d(TAG,"ENTERED")
            val questionRef = db.collection("answered")
                .whereEqualTo("userID", currentUser )
                .whereGreaterThanOrEqualTo("date",from)
                .whereLessThanOrEqualTo("date",to)
                .orderBy("date",Query.Direction.DESCENDING)

            val query = questionRef.get().await()

            historyHeatData.clear()
            historyChartData.clear()

            for (document in query) {
                val entry: AnsweredQuestion = document.toObject<AnsweredQuestion>()
                Log.d(TAG,"ANOTHER ANSWERED QUESTION DATA: ${entry.date}")

                //GET CLARITY
                val reviewRef = db.collection("reviews")
                    .whereEqualTo("answeredQuestionID", document.id)
                    .aggregate(
                        AggregateField.average("clarity"),
                        AggregateField.average("understanding")
                    )
                val reviewQuery = reviewRef.get(AggregateSource.SERVER).await()
                var clarityData = reviewQuery.get(AggregateField.average("clarity"))
                if (clarityData == null) clarityData = 0.0

                var understandingData = reviewQuery.get(AggregateField.average("understanding"))
                if (understandingData == null) understandingData = 0.0

                val reviewScores = listOf(Pair("clarity",clarityData),Pair("understanding",understandingData))

                val currentHistory: History = History(entry.textResponse,document.id,reviewScores,"")
                historyChartData.add(currentHistory)
                if (historyHeatData.containsKey(entry.date)) {
                    historyHeatData[entry.date] = historyHeatData[entry.date]!! + 1
                } else {
                    historyHeatData[entry.date] = 1
                }
            }
        }
    }

    private suspend fun fetch(ft: FetchType, func: suspend () -> Unit) {
        if (!isCached.getValue(ft)) {
            Log.d(TAG,"No cache found for ${ft}, fetching now...")
            func()
        }
        else {
            Log.d(TAG, "Found cache for $ft")
        }
        isCached[ft] = true
        notifySubscribers()

    }
    fun reset() {
        currentReviewData.clear()
        currentIteration = 1
        invalidateAll()
        notifySubscribers()
    }

//    fun isCached.getValue(ft: FetchType): Boolean {
//        return when (ft) {
//            FetchType.PROFILE-> (user == null)
//            FetchType.LEADERBOARD-> (leaderBoardStandings.isEmpty())
//            FetchType.SEARCH-> false
//            FetchType.RECOMMENDATION->false
//            FetchType.RESETUSER->true
//            FetchType.NOTIFICATION->(notificationCount == 0)
//            FetchType.HISTORY -> true
//        }
//    }


    companion object {
        private const val TAG = "MainModel"
    }
}