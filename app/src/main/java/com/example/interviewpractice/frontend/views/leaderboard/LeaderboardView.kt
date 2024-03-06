package com.example.interviewpractice.frontend.views.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
@Preview
fun LeaderboardView()
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Leaderboard",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )
        Podium(leaderboardData[0], leaderboardData[1], leaderboardData[2])
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(leaderboardData.size) { index ->
                if (index >= 3) {
                    LeaderboardItem(leaderboardData[index])
                }
            }
        }
    }
}

@Composable
fun Podium(firstPlace: LeaderboardEntry, secondPlace: LeaderboardEntry, thirdPlace: LeaderboardEntry) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in intArrayOf(1, 0 ,2)) {
            val podiumHeight = if (i == 0) 90.dp else (3 - i) * 30.dp
            val color = if (i == 0) Color(0xFFFFD700) else if (i == 1) Color(0xFFC0C0C0) else Color(0xFFCD7F32)
            val modifier = Modifier
                .width(80.dp)
                .height(podiumHeight)
                .background(color = color)

            var entry = firstPlace
            if (i == 1) { entry = secondPlace} else if (i == 2) { entry = thirdPlace }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text="${entry.score}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                )
                Box(modifier = modifier) {
                    Text(
                        text = entry.username,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderboardItem(leaderboardEntry: LeaderboardEntry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = leaderboardEntry.rank.toString(), modifier = Modifier.weight(1f))
            Text(text = leaderboardEntry.username, modifier = Modifier.weight(2f))
            Text(text = leaderboardEntry.score.toString(), modifier = Modifier.weight(1f))
        }
    }
}


//move structure to backend
data class LeaderboardEntry(
    val rank: Int,
    val username: String,
    val score: Int
)

//dummy data
val leaderboardData = listOf(
    LeaderboardEntry(1, "User1", 500),
    LeaderboardEntry(2, "User2", 480),
    LeaderboardEntry(3, "User3", 460),
    LeaderboardEntry(4, "User4", 440),
    LeaderboardEntry(5, "User5", 420),
    LeaderboardEntry(6, "User6", 400),
    LeaderboardEntry(7, "User7", 380),
    LeaderboardEntry(8, "User8", 360),
    LeaderboardEntry(9, "User9", 340),
    LeaderboardEntry(10, "User10", 320)
)
