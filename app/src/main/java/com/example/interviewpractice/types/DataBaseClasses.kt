package com.example.interviewpractice.types

import android.net.Uri
import java.util.Date

//Add more user fields here, like birthday, first/last name, photo url?, etc
data class User(
    val username: String = "",
    val email: String = "",
    val questionsAnswered: Int = 0,
    val fieldsOfInterest: List<Tag> = emptyList(),
    val birthday: Date = Date(),
    val pfpURL: String = ""
)

data class Question(
    val questionText: String = "",
    val tags: List<Tag> = emptyList(),
    val userID: String = "",
    val date: Date = Date(),
    val answers: List<String> = emptyList(),
    var questionID: String = ""
)

data class History(
    val questionText: String = "",
    val questionId: String = "",
    val reviewScores: List<Pair<String,Double>> = emptyList(),
    val audioUrl: String = "",


//    val playBarViewModel = PlayBarViewModel()
//    val questionViewModel = QuestionViewModel()
)

//Access Reviews from the Firestore with the same ID as the answered_question
//We split it up to avoid nesting, which is inefficient. (SEE: https://firebase.google.com/docs/database/web/structure-data#avoid_nesting_data)
data class Review(
    val userID: String = "", //Who reviewed the question
    val answeredQuestionID: String = "", //The answered question this review refers to
    val answeredQuestionAuthorID: String = "", //The id of the person who answered this question
    val reviewText: String = "", //What the review says
    val understanding: Int = 1, //Understanding score, out of 5
    val clarity: Int = 1, //Clarity score, out of 5
    val date: Date = Date()
)

data class AnsweredQuestion(
    val questionText: String = "",
    val userID: String = "", //Who answered the question
    var downloadUrl: String = "", //Some sort of link to the Google Cloud storage
    val audioTime: Int = 0, //In seconds
    val questionID: String = "",
    val date: Date = Date(),
    val reviewCount: Int = 0, //Number of reviews this question has
    val tags: List<Tag> = emptyList()
)

data class HasReviewed(
    val hasReviewed: Boolean = true
)

//Format as needed
data class Notification(
    val notificationText: String = "",
    val type: NotificationType = NotificationType.NEWREVIEW,
    val questionID: String = "",
    val reviewID: String = "",
    val userID: String = "", //Who the notification is for
    val review: Review = Review(),
    val answeredQuestion: AnsweredQuestion = AnsweredQuestion()
)

enum class Tag(val v: String) {
    BIOLOGY("biology"),
    ENGLISH("english"),
    CHEMISTRY("chemistry"),
    ART("art"),
    CS("computer science"),
    MATH("math"),
    FINANCE("finance"),
    PHYSICS("physics"),
    BUSINESS("business")
}