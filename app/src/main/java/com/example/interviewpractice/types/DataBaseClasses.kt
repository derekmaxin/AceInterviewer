package com.example.interviewpractice.types

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
    val hasVoice: Boolean = false,
    val hasText: Boolean = false,
    val deprecated: Boolean = false,
    val userID: String = ""
)

//Access Reviews from the Firestore with the same ID as the answered_question
//We split it up to avoid nesting, which is inefficient. (SEE: https://firebase.google.com/docs/database/web/structure-data#avoid_nesting_data)
data class Review(
    val userID: String = "", //Who reviewed the question
    val answeredQuestionID: String = "", //The answered question this review refers to
    val answeredQuestionAuthorID: String = "", //The id of the person who answered this question
    val reviewText: String = "", //What the review says
    val reviewScore: ReviewScore = ReviewScore(),
    val date: String = ""
)

data class ReviewScore(
    val understanding: Int = 0, //Understanding score, out of 5
    val clarity: Int = 0 //Clarity score, out of 5
)

data class AnsweredQuestion(
    val userID: String = "", //Who answered the question
    val textResponse: String = "",
//    val audioResponse: ?? //Some sort of link to the Google Cloud storage
    val audioTime: Int = 0, //In seconds
    val isPrivate: Boolean = false,
    val questionID: String = ""
)

//Format as needed
data class Notification(
    val notificationText: String = "",
    val type: NotificationType = NotificationType.NEWREVIEW,
    val typeID: String,
    val userID: String //Who the notification is for
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