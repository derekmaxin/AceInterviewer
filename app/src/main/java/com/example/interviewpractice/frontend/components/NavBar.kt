package com.example.interviewpractice.frontend.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
//@Preview
fun NavBar(goToSearch: () -> Unit, goToHome: () -> Unit, goToProfile: () -> Unit) {
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
                    IconButton(onClick = { /* Handle navigation action */ }) {
                        Icon(Icons.Default.Star, contentDescription = "Reviews")
                    }
                    IconButton(onClick = goToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = goToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /* Handle navigation action */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Settings")
                    }
                    IconButton(onClick = goToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            }
        )
    }
}