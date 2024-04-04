package com.example.interviewpractice.frontend.views.profile

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.interviewpractice.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.LaunchedEffect
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.frontend.components.historychart.HistoryChart
import com.example.interviewpractice.frontend.components.userbadge.UserBadgeDisplay
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.controller.QuestionController
import coil.compose.rememberImagePainter
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.frontend.components.history.History
import com.example.interviewpractice.frontend.components.history.HistoryViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun ProfileView(mm: MainModel,
                c: QuestionController,
                goToLeaderboard: () -> Unit,
                ac: AuthController,
                uc: UserController,
                hc: HistoryController
) {
    val vm: ProfileViewModel = viewModel()
    vm.addModel(mm)
    val historyViewModel: HistoryViewModel = viewModel()
    historyViewModel.addModel(mm)



    LaunchedEffect(Unit){
        c.fetchData(FetchType.PROFILE)
    }

    //For the pfp
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uc.addUserPfp(it, context)
        }
    }

    Surface() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(vertical = 16.dp)
                .padding(bottom = 65.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {
                ProfilePicture(
                    painter = vm.user?.pfpURL?.let { rememberImagePainter(data = it) },
                    contentDescription = "Profile Picture",
                    onClick = { launcher.launch("image/*") },
                    hasNoPfp = vm.user?.pfpURL == ""
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(text = vm.user?.username ?: "", style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "@${vm.user?.username ?: ""}", style = TextStyle(fontSize = 10.sp))
                    Text(text = vm.user?.email ?: "", style = TextStyle(fontSize = 10.sp))
                    val birthdayText = vm.user?.birthday?.let {
                        vm.formatDate(it)
                    } ?: "unknown birthday"
                    Text(
                        text = birthdayText,
                        style = TextStyle(fontSize = 10.sp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                HistoryButton()
                Spacer(modifier = Modifier.width(16.dp))
                SettingsButton()
                Spacer(modifier = Modifier.width(16.dp))
                LogoutButton(ac)
            }
            Spacer(modifier = Modifier.height(16.dp))
            UserBadgeDisplay(vm.badgeInfo)

            Spacer(modifier = Modifier.height(8.dp))

            HistoryChart()

            History(historyViewModel, hc)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {  }
            ) {
                Text(text = "Your Proficiency Scores")
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
    painter: Painter?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    hasNoPfp: Boolean
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .padding(16.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (hasNoPfp) {
            //default icon
            Image(
                painter = painterResource(id = R.drawable.defaultpfp),
                contentDescription = "Add Photo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "   add \n profile \n picture")
        }
        painter?.let {
            Image(
                painter = it,
                contentDescription = contentDescription,
                modifier = Modifier.size(100.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        } ?: run {
            // If painter is null, show default icon
            Image(
                painter = painterResource(id = R.drawable.defaultpfp),
                contentDescription = "Add Photo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "   add \n profile \n picture")
        }
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
fun SettingsButton() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle button click */ }
    ) {
        Text(
            text = "Settings",
            style = TextStyle(fontSize = 10.sp)
        )
        IconButton(
            onClick = { /* Handle button click */ },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}

@Composable
fun LogoutButton(ac: AuthController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle button click */ }
    ) {
        Text(
            text = "Log out",
            style = TextStyle(fontSize = 10.sp)
        )
        IconButton(
            onClick = { ac.verifyLogout() },
            modifier = Modifier.size(30.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out")
        }
    }
}

