package com.example.interviewpractice.frontend.components.playbar

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp




@Composable
fun PlayBar(playBarViewModel: PlayBarViewModel) {

    DisposableEffect(Unit) {
        onDispose {
            playBarViewModel.mps.stop()
            playBarViewModel.mps.release()
            Log.d("MPS", "Released")
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        IconButton(onClick = { playBarViewModel.switchState() }) {
            if (playBarViewModel.playState == PlayState.PAUSE) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play")
            } else {
                Icon(Icons.Filled.Pause, contentDescription = "Pause")
            }

        }
        Slider(
            modifier = Modifier.weight(1f),
            value = playBarViewModel.mps.currentPosition,
            onValueChange = {
                playBarViewModel.mps.currentPosition = it
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = Color(0xffd4c7eb),
            ),
            valueRange = 0f..(playBarViewModel.mps.audioLength.toFloat())
        )
        Text(
            text = (playBarViewModel.mps.audioLength / 60000).toString() + ":" + String.format("%02d",((playBarViewModel.mps.audioLength / 1000) % 60)).toString(),
            modifier = Modifier
                .widthIn(64.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
