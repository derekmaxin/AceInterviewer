package com.example.interviewpractice.frontend.components.navbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.NotificationController
import com.example.interviewpractice.frontend.views.notifications.NotificationsViewModel
import com.example.interviewpractice.frontend.views.search.SearchViewModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType

@Composable
//@Preview
fun NavBar(
    goToReviews: () -> Unit,
    goToSearch: () -> Unit,
    goToHome: () -> Unit,
    goToProfile: () -> Unit,
    goToNotifications: () -> Unit,
    goToQuestion: () -> Unit,
    nc: NotificationController,
    mm: MainModel) {

    LaunchedEffect(Unit){
        nc.listenForNotifications()
    }

    val vm: NavBarViewModel = viewModel()
    vm.addModel(mm)

    val ICON_SIZE = 42.dp



    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomAppBar(
//            contentColor = Color.Red,
//            contentPadding = PaddingValues(16.dp), // Add padding as needed
            content = {
                Row(
//                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = goToReviews ) {
                        Icon(Icons.Default.Star, contentDescription = "Reviews", modifier = Modifier.size(ICON_SIZE))
                    }
                    IconButton(onClick = goToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search", modifier = Modifier.size(ICON_SIZE))
                    }
                    IconButton(onClick = goToHome) {
                        Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(ICON_SIZE))
                    }
                    BadgedBox(
                        badge = {
                            if (vm.notificationCount > 0) { // Only show badge if there are notifications
                                Badge {
                                    Text(text = vm.notificationCount.toString(), color = Color.White)
                                }
                            }
                        }
                    ) {
                        IconButton(onClick = goToNotifications) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", Modifier.size(ICON_SIZE))
                        }
                    }
                    IconButton(onClick = goToProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile",Modifier.size(ICON_SIZE))
                    }

                    IconButton(onClick = goToQuestion) {
                        Icon(Icons.Filled.QuestionMark, contentDescription = "Profile", Modifier.size(ICON_SIZE))
                    }
                }
            }
        )
    }
}