package com.example.interviewpractice.frontend.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
//@Preview
fun NavBar(goToReviews: () -> Unit, goToSearch: () -> Unit, goToHome: () -> Unit, goToProfile: () -> Unit, goToNotifications: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomAppBar(
            contentPadding = PaddingValues(16.dp), // Add padding as needed
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = goToReviews ) {
                        Icon(Icons.Default.Star, contentDescription = "Reviews")
                    }
                    IconButton(onClick = goToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = goToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = goToNotifications) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = goToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            }
        )
    }
}