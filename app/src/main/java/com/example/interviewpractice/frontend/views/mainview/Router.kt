package com.example.interviewpractice.frontend.views.mainview

import androidx.navigation.NavHostController

class Router(anc: NavHostController) {
    val goToAnswerQuestion: () -> Unit = {
        anc.navigate("answer question")
    }
    val goToMakeQuestion: () -> Unit = {
        anc.navigate("make question")
    }
    val goToHome: () -> Unit = {
        anc.navigate("home")
    }
    val goToReview: () -> Unit = {
        anc.navigate("reviews")
    }
}