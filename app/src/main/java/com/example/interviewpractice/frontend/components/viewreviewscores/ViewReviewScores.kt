package com.example.interviewpractice.frontend.components.viewreviewscores

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.review.ReviewScoreComponent
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.History


@Composable
fun ViewReviewScores(history: History, mm: MainModel) {
//    val playBarViewModel = PlayBarViewModel()
//    playBarViewModel.audioURL = history.audioUrl
//    playBarViewModel.audioLength = 20000

    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp).clickable(onClick = { }),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Text(
                text = history.questionText,
                style = MaterialTheme.typography.bodyLarge,
            )

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            PlayBar(mm, history.audioUrl,history.audioTime)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
//            PlayBar(viewModel.playBarViewModel)

            for (scoreComponent in history.reviewScores) {
                ReviewScoreComponent(scoreComponent.first, scoreComponent.second.toInt())
            }
        }
    }
}