package com.example.interviewpractice.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.interviewpractice.controller.Controller
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.MMViewModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.UserException
import java.util.Date


fun getCurrentDate(): Date {
    return Date()
}

fun verifyGenericString(str: String, field: String) {
    if (str.isEmpty()) {
        throw UserException("$field field is empty")
    }
}

@Composable
fun Lifecycle(vm: MMViewModel, c: Controller, ft:FetchType) {
    LaunchedEffect(Unit) {
        c.fetchData(ft)
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.unsubscribe()
        }
    }
}

fun navToQuestion(nav: ()->Unit, question: Question,c: QuestionController) {
    c.loadNextQuestion(question)
    nav()
}

