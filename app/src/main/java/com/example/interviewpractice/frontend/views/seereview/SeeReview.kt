package com.example.interviewpractice.frontend.views.seereview

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.Notification

@Composable
fun SeeReviewView(mm: MainModel, reviewId: String) {
    Surface(){
        Column(){
            Text("the review id is $reviewId")
            Log.d("ReviewId ","$reviewId")
        }
    }
}