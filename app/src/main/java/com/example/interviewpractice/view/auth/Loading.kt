package com.example.interviewpractice.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewpractice.R

@Composable
@Preview
fun Loading() {
    Image(
        painter = painterResource(id = R.drawable.loading),
        contentDescription = "Your Image",
        modifier = Modifier.fillMaxWidth().height(500.dp).padding(vertical = 8.dp),
    )
}
