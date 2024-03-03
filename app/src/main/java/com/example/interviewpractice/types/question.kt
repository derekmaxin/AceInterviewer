package com.example.interviewpractice.types

data class Question(
    val questionText: String,
    val tags: MutableList<Tags> = mutableListOf<Tags>(),
    val hasVoice: Boolean,
    val hasText: Boolean,
    val deprecated: Boolean
)

//Access Reviews from the Firestore with the same ID as the answered_question
//We split it up to avoid nesting, which is inefficient. (SEE: https://firebase.google.com/docs/database/web/structure-data#avoid_nesting_data)
data class Review(
    val userID: String //Who reviewed the question
)

data class AnsweredQuestion(
    val userID: String, //Who answered the question
    val textResponse: String,
//    val audioResponse: ?? //Some sort of link to the Google Cloud storage
    val audioTime: Int, //In seconds
    val isPrivate: Boolean,
    val questionID: Int
)




enum class Tags(val v: String) {
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