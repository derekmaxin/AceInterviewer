package com.example.interviewpractice.frontend.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.frontend.components.NavBar
import com.example.interviewpractice.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
@Preview
fun ProfileView() {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                Spacer(modifier = Modifier.width(48.dp))
                HistoryButton()
                Spacer(modifier = Modifier.width(16.dp))
                ProgressButton()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
                    .background(Color.Gray)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Badge(painter = painterResource(id = R.drawable.klee),
                        contentDescription = "Badge", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(painter = painterResource(id = R.drawable.klee),
                        contentDescription = "Badge", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(painter = painterResource(id = R.drawable.klee),
                        contentDescription = "Badge", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "badge display"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 4.dp)
                    .clickable { /* Handle button click */ },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize(),
                    ) {
                    HorizontalDivider(modifier = Modifier.width(120.dp))
                    Text(
                        text = "Click to expand",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(modifier = Modifier.width(120.dp))
                }
            }
            //PLACEHOLDER
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ){}
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

@Composable
fun Badge(painter: Painter,
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