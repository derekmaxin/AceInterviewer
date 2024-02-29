package com.example.interviewpractice.frontend

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
@Preview
fun NavBar() {
    BottomAppBar(
        contentPadding = PaddingValues(16.dp), // Add padding as needed
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Handle navigation action */ }) {
                    Icon(Icons.Default.Star, contentDescription = "Home")
                }
                IconButton(onClick = { /* Handle navigation action */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }

                IconButton(onClick = { /* Handle navigation action */ }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = { /* Handle navigation action */ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Settings")
                }
                IconButton(onClick = { /* Handle navigation action */ }) {
                    Icon(Icons.Default.Person, contentDescription = "Settings")
                }
            }
        }
    )
}