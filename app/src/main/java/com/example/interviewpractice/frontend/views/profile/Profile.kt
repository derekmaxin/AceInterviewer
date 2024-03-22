package com.example.interviewpractice.frontend.views.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Done
import com.example.interviewpractice.frontend.components.historychart.HistoryChart
import com.example.interviewpractice.frontend.components.userbadge.UserBadgeDisplay



@Composable
//@Preview
fun ProfileView(profileViewModel: ProfileViewModel, goToLeaderboard: () -> Unit) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {
                ProfilePicture(
                    painter = painterResource(id = R.drawable.klee),
                    contentDescription = "Profile Picture"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(text = "Derek Maxin", style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "@dmaxin", style = TextStyle(fontSize = 10.sp))
                    Text(text = "dmaxin@uwaterloo.ca", style = TextStyle(fontSize = 10.sp))
                }
                Spacer(modifier = Modifier.width(36.dp))
                HistoryButton()
                Spacer(modifier = Modifier.width(16.dp))
                ProgressButton()
            }
            Spacer(modifier = Modifier.height(16.dp))
            UserBadgeDisplay(profileViewModel.badgeInfo)

            //PLACEHOLDER

//            Spacer(modifier = Modifier.height(8.dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .background(Color.Gray),
//                contentAlignment = Alignment.Center
//            ){}
            HistoryChart()
            //END OF PLACEHOLDER
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  }
            ) {
                Text(text = "Your Proficiency Scores")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  }
            ) {
                Text(text = "Your Top Rated Answers")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  }
            ) {
                Text(text = "Your Best Asked Questions")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = goToLeaderboard
            ) {
                Text(text = "Leaderboards")
            }
        }
    }
}

@Composable
fun ProfilePicture(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .padding(16.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun HistoryButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle button click */ }
    ) {
        Text(
            text = "History",
            style = TextStyle(fontSize = 10.sp)
        )
        IconButton(
            onClick = { /* Handle button click */ },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(Icons.Default.AccountBox, contentDescription = "History")
        }
    }
}

@Composable
fun ProgressButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle button click */ }
    ) {
        Text(
            text = "Progress",
            style = TextStyle(fontSize = 10.sp)
        )
        IconButton(
            onClick = { /* Handle button click */ },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(Icons.Default.Done, contentDescription = "Progress")
        }
    }
}

