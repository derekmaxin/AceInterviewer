package com.example.interviewpractice.frontend.views.profile

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.example.interviewpractice.controller.UserController
import com.example.interviewpractice.frontend.components.userbadge.UserBadgeDisplay
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interviewpractice.controller.AuthController
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.FetchType
import coil.compose.rememberImagePainter
import com.example.interviewpractice.controller.HistoryController
import com.example.interviewpractice.frontend.components.history.History
import com.example.interviewpractice.frontend.components.history.HistoryViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
//@Preview
fun ProfileView(mm: MainModel,
                c: UserController,
                goToLeaderboard: () -> Unit,
                ac: AuthController,
                uc: UserController,
                hc: HistoryController,
) {
    val vm: ProfileViewModel = viewModel()
    val historyViewModel: HistoryViewModel = viewModel()


    LaunchedEffect(Unit) {
        vm.addModel(mm)
        historyViewModel.addModel(mm)
        uc.fetchData(FetchType.PROFILE)
        hc.fetchData(FetchType.HISTORY)
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("PROFILE.KT","DISCONNECTING")
            vm.unsubscribe()
            historyViewModel.unsubscribe()
        }
    }

//    For the pfp
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        c.am.loading +=1
        c.am.isInit = true
        uri?.let {
            mm.invalidate(FetchType.PROFILE)
            mm.invalidate(FetchType.HISTORY)
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
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    "Your Profile",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {
                ProfilePicture(
                    painter = vm.user?.pfpURL?.let { rememberImagePainter(data = it) },
                    contentDescription = "Profile Picture",
                    onClick = {
                        launcher.launch("image/*") },
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
                Spacer(modifier = Modifier.width(96.dp))
                LogoutButton(ac)
            }
            Spacer(modifier = Modifier.height(16.dp))
            vm.user?.let { UserBadgeDisplay(it.questionsAnswered) }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Selected Fields of Interest",
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            val interestsText = buildString {
                if (vm.user?.fieldsOfInterest?.isNotEmpty() == true) {
                    vm.user!!.fieldsOfInterest.forEachIndexed { index, value ->
                        if (index > 0) {
                            append(", ")
                        }
                        append(value.v)
                    }
                } else {
                    append("None")
                }
            }

            if (vm.user?.fieldsOfInterest?.isNotEmpty() == true) {
                FlowRow(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    vm.user!!.fieldsOfInterest.forEachIndexed { _, value ->
                        FilterChip(
                            selected = true,
                            onClick = { },
                            label = { Text(value.v) })
                    }
                }
            } else {
                Text(text = "No interests selected", modifier = Modifier.padding(horizontal = 8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Leaderboard",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = goToLeaderboard
            ) {
                Text(text = "Go to Leaderboard →")
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Reviewed Answers",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            History(historyViewModel, hc, mm)
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
fun LogoutButton(ac: AuthController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
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

