package com.example.interviewpractice.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.interviewpractice.view.HomeScreen
import com.example.interviewpractice.view.auth.LoginScreen

@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    val navGraph = remember(navController) {
        navController.createGraph(startDestination = "login") {
            composable("login") { LoginScreen(
                onLoginClick = { navController.navigate("home") }
            ) }
            composable("home") { HomeScreen() }
        }
    }
    NavHost(navController = navController, graph = navGraph)
}