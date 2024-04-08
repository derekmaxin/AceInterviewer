package com.example.interviewpractice.frontend.components.viewreviewscores

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.interviewpractice.frontend.components.playbar.PlayBar
import com.example.interviewpractice.frontend.components.playbar.PlayBarViewModel
import com.example.interviewpractice.frontend.components.question.DummyQuestion
import com.example.interviewpractice.frontend.components.question.Question
import com.example.interviewpractice.frontend.components.question.QuestionViewModel
import com.example.interviewpractice.frontend.components.review.Review
import com.example.interviewpractice.frontend.components.review.ReviewScoreComponent
import com.example.interviewpractice.frontend.components.review.ReviewViewModel
import com.example.interviewpractice.types.History
import com.example.interviewpractice.types.Notification
import kotlin.math.floor


@Composable
fun ViewReviewScores(history: History) {
    val playBarViewModel = PlayBarViewModel()
    playBarViewModel.audioURL = history.audioUrl
    Log.d("vrs", "${history.audioUrl}")
    playBarViewModel.audioLength = 20000

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
<<<<<<< app/src/main/java/com/example/interviewpractice/frontend/components/viewreviewscores/ViewReviewScores.kt
            PlayBar(playBarViewModel)
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
=======
//            PlayBar(viewModel.playBarViewModel)
>>>>>>> app/src/main/java/com/example/interviewpractice/frontend/components/viewreviewscores/ViewReviewScores.kt

            for (scoreComponent in history.reviewScores) {
                ReviewScoreComponent(scoreComponent.first, scoreComponent.second.toInt())
            }
        }
    }
}