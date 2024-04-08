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
import com.example.interviewpractice.types.HasReviewed
import com.example.interviewpractice.types.History
import com.example.interviewpractice.types.Notification
import com.example.interviewpractice.types.NotificationType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.SystemException
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.AggregateField
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.Date
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MainModel(
    private val auth: FirebaseAuth = Firebase.auth,
    private val db: FirebaseFirestore = Firebase.firestore,
    private val storage: FirebaseStorage = Firebase.storage
    ) : Presenter() {

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
    var searchResults = mutableListOf<Pair<Question,Boolean>>()

    //LEADERBOARD
    var leaderBoardStandings = mutableListOf<User>()

    //RECOMMENDATION
    var homePageRecommendations: Question? = null

    //HISTORY
    var historyChartData = mutableListOf<History>()

    //NOTIFICATION
    var newReviewNotifications = mutableListOf<Notification>()
    var notificationCount = 0
    var notificationListener: ListenerRegistration? = null

    //PROFILE
    var user: User? = null

    //QUESTION
    var currentQuestionData: Question? = null

    //TINDER
    var currentReviewData = mutableListOf<AnsweredQuestion>()
    var currentIdData = mutableListOf<String>()

    //
    var userQuestions = mutableListOf<Question>()

    //ANSWER
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
            val uploadTask = imageRef.putStream(stream).await()

            // Register observers to listen for upload success or failure
                // Image uploaded successfully
                // Get the download URL of the image
                val downloadUri = imageRef.downloadUrl.await()
                    // Store the download URL in Firestore or Realtime Database
                    userRef.update("pfpURL", downloadUri.toString()).await()
        } ?: run {
            Log.e(TAG, "Failed to open input stream for URI: $uri")
        }
    }

    suspend fun removeNotification(reviewID: String, uid: String) {
        val notificationRef = db.collection("notifications")
            .whereEqualTo("reviewID",reviewID)
            .whereEqualTo("userID", uid)
            .limit(1)
        invalidate(FetchType.NOTIFICATION)
        val documentRef = notificationRef.get().await()
        for (doc in documentRef) {
            //SHOULD ONLY RUN ONCE
            if (!doc.exists()) throw CatastrophicException("No notification found with the specifications")
            db.collection("notifications").document(doc.id).delete().await()
        }

        Log.d(TAG,"Remove notification successful")
    }

    suspend fun addQuestion(question: Question) {
        val ref: DocumentReference = db.collection("my_collection").document()
        val qid: String = ref.id
        question.questionID = qid
        db.collection("questions").document(qid).set(question).await()

        db.collection("questions")
        Log.d(TAG,"addQuestion:success")
    }

    suspend fun addAnsweredQuestion(question: AnsweredQuestion, fileUri: String) {
        invalidate(FetchType.HISTORY)
        invalidate(FetchType.TINDER)
        invalidate(FetchType.ANSWERED)
//        Log.d(TAG,"INVALIDATED ANSWERED: ${isCached[FetchType.ANSWERED]}")
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

        Log.d(TAG,"addAnsweredQuestion:success")
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
        // Add review itself
        val reviewRef = db.collection("reviews").add(review).await()

        // Fetch answered data asynchronously
        val answeredRef = db.collection("answered").document(review.answeredQuestionID)
        val documentSnapshot = answeredRef.get().await()

        val answeredData = documentSnapshot.toObject<AnsweredQuestion>()
            ?: throw SystemException("No answered question with this ID found")


        // Add notification
        val notification = Notification(
            notificationText = "A new review was added for your answer to question \"${answeredData.questionText}\"",
            type = NotificationType.NEWREVIEW,
            questionID = review.answeredQuestionID,
            userID = review.answeredQuestionAuthorID,
            reviewID = documentSnapshot.id,
            review = review,
            answeredQuestion = answeredData
        )

        db.collection("notifications").add(notification).await()

        db.collection("users").document(review.userID).collection("hasReviewed").document(review.answeredQuestionID).set(HasReviewed())

        db.collection("answered").document(review.answeredQuestionID).update("reviewCount", FieldValue.increment(1))

        Log.d(TAG, "addReview::success")
    }

    suspend fun searchQuestion(queryText: String, uid: String, filters: List<Tag> = emptyList(),self:Boolean=false,completed: Boolean = false) {
        val questionsRef = db.collection("questions")
        var filtersvar = filters

        if (self) {
            getCurrentUserData()
            var currUser = user
            if (currUser == null) throw CatastrophicException("No user found despite being logged in")
            filtersvar = currUser.fieldsOfInterest
        }
        var searchRef = questionsRef
            .whereGreaterThanOrEqualTo("questionText", queryText)
            .whereLessThanOrEqualTo("questionText", queryText + "\uf8ff")

        if (filtersvar.isNotEmpty()) {
            searchRef = questionsRef
                .whereGreaterThanOrEqualTo("questionText", queryText)
                .whereLessThanOrEqualTo("questionText", queryText + "\uf8ff")
                .whereArrayContainsAny("tags", filtersvar )
        }
        val query = searchRef.get().await()


        searchResults.clear()
        for (entry in query) {
            Log.d(TAG, "QUERY ${entry.id} => ${entry.data}")
            val question = entry.toObject<Question>()
                val answeredQuestionRef = db.collection("answered")
                    .whereEqualTo("userID",uid)
                    .whereEqualTo("questionID",entry.id)
                val aquery = answeredQuestionRef.get().await()
                if (aquery.isEmpty && !completed) {
                    searchResults.add(Pair(question,false))
                }
                for (aq in aquery) {
                    Log.d(TAG, "AQ ${aq.id} => ${aq.data}")
                    if (!aq.exists()) {
                        if (!completed) {
                            searchResults.add(Pair(question,false))
                            break
                        }
                    }
                    else {
                        searchResults.add(Pair(question,true))
                        break
                    }
                }


        }
        if (self) {
            if (searchResults.isNotEmpty()) {
                for (question in searchResults) {
                    if (!question.second) {
                        homePageRecommendations = question.first
                        return
                    }
                }
                homePageRecommendations = Question("We have no questions in your field of interest!",
                    emptyList(),"", getCurrentDate(), emptyList()
                )
            }
            else {
                homePageRecommendations = Question("We have no questions in your field of interest!",
                    emptyList(),"", getCurrentDate(), emptyList()
                )
            }

        }
        isCached[FetchType.SEARCH] = true
        notifySubscribers()


    }

    fun addNotificationListener(currentUserID: String) {
        invalidate(FetchType.NOTIFICATION)
        invalidate(FetchType.HISTORY)
        //ASSUME ONLY NEW REVIEW NOTIFICATIONS (FOR NOW FOR SIMPLICITY)
        val docRef = db.collection(Collections.notifications.toString()).whereEqualTo("userID",currentUserID)

        notificationListener = docRef.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        //New review came in
                        val notification = dc.document.toObject<Notification>()
//                        val notification = Notification(
//                            notificationText = "A new review was added for question ${review.answeredQuestionID}",
//                            type = NotificationType.NEWREVIEW,
//                            reviewID = dc.document.id,
//                            questionID = review.answeredQuestionID,
//                            userID = currentUserID)
//                        Log.d(TAG,"NOTIFICATION DATA: $notification")
                        newReviewNotifications.add(notification)
                        if (notificationCount == -1) {
                            notificationCount = 0
                        }
                        notificationCount += 1
                    }
                    DocumentChange.Type.MODIFIED -> {
                        //MIGHT NEED TO CHANGE
                        Log.d(TAG, "Notification changed: ${dc.document.data}")
                    }
                    DocumentChange.Type.REMOVED -> {
                        val notification = dc.document.toObject<Notification>()
                        newReviewNotifications.remove(notification)
                        notificationCount -= 1
                        Log.d(TAG, "Notification removed: ${dc.document.data}")
                    }


                }
            }
            invalidate(FetchType.NOTIFICATION)
            notifySubscribers()

        }
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
        invalidate(FetchType.LEADERBOARD)
        if (uid != null) {
            val userRef = db.collection("users").document(uid)
            // Atomically increment the population of the city by 50.
            userRef.update("questionsAnswered", FieldValue.increment(1))

            leaderBoardStandings.clear()
            notifySubscribers()

        }
        else {
            throw SystemException("No uid for signed in user")
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
                throw SystemException("No uid for signed in user")
            }
        }
    }

//    suspend fun getReviewData(reviewId : String){
//        fetch(FetchType.REVIEW) {
//            if (reviewId != null) {
//                val userDoc = db.collection("reviews").document(reviewId)
//                val query = userDoc.get().await()
//
//                //review = query.toObject<User>()
//            }
//            else {
//                throw CatastrophicException("Review with this id does not exist")
//            }
//        }
//    }
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
            getCurrentUserData()

            if (user ==null) throw CatastrophicException("No user data found")
            val foi = user!!.fieldsOfInterest

            val questionRef = db.collection("answered")
                .orderBy("reviewCount",Query.Direction.ASCENDING)
                .limit(10)

            val query = questionRef.get().await()

            val reviewRef = db.collection("reviews")
                .whereEqualTo("userID",currentUser)


            currentReviewData.clear()
            for (entry in query ) {
                val answered = entry.toObject<AnsweredQuestion>()
                if (answered.tags.any { it in foi } && answered.userID != currentUser) {

                    var docRef = db.collection("users")
                        .document(currentUser)
                        .collection("hasReviewed").document(entry.id);
                    val res = docRef.get().await()
                    if (!res.exists()) {
                        currentReviewData.add(answered)
                        currentIdData.add(entry.id)
                    }
                    else {
                        Log.d(TAG,"ALREADY REVIEWED THIS QUESTION")
                    }

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
    suspend fun getUserAnswered() {
        fetch(FetchType.ANSWERED) {
            val questionsRef = db.collection("answered").whereEqualTo("userID", auth.currentUser?.uid)
            val query = questionsRef.get().await()
            userAnswered.clear()
            for (question in query) {
//                Log.d(TAG, "QUERY ${question.id} => ${question.data}")
                userAnswered.add(question.toObject(AnsweredQuestion::class.java))
            }
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

                val reviewScores = listOf(Pair("clarity",clarityData),Pair("completeness",understandingData))

                Log.d(TAG,"DOWNLOADURL: ${entry.downloadUrl}")
                val currentHistory: History = History(entry.questionText,document.id,reviewScores,entry.downloadUrl, entry.audioTime)
                historyChartData.add(currentHistory)
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