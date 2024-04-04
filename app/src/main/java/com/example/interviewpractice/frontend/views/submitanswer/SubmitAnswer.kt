package com.example.interviewpractice.frontend.views.submitanswer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.interviewpractice.controller.QuestionController
import com.example.interviewpractice.frontend.views.answerquestion.AnswerQuestionViewModel
import java.io.File
import java.io.IOException
import java.util.UUID

class AudioRecord(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private val outputFile = File(context.getExternalFilesDir(null), "${UUID.randomUUID()}.3gp")


    @RequiresApi(Build.VERSION_CODES.S)
    fun onRecord(start: Boolean, qc: QuestionController, vm: AnswerQuestionViewModel) {
        if (start) {
            Log.d("RECORDER", "Started Recording")
            startRecording()
        } else {
            Log.d("RECORDER", "Stopped Recording")
            stopRecording(qc, vm)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        recorder = MediaRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)

            try {
                prepare()
                Log.d("RECORDER", "Recording prepared")
            } catch (e: IOException) {
                Log.e("RECORDER", "prepare() failed: ${e.message}")
            }

            start()
            Log.d("RECORDER", "Recording started")
        }
    }

    private fun stopRecording(qc: QuestionController, vm: AnswerQuestionViewModel) {
        recorder?.apply {
            try {
                stop()
                Log.d("RECORDER", "Recording stopped")
            } catch (e: IllegalStateException) {
                Log.e("RECORDER", "Failed to stop recording: ${e.message}")
            }
            reset()
            try {
                release()
                Log.d("RECORDER", "MediaRecorder released")
            } catch (e: IllegalStateException) {
                Log.e("RECORDER", "Failed to release MediaRecorder: ${e.message}")
            }
        }
        recorder = null

        val audioFile = File(outputFile.absolutePath)
        if (audioFile.exists()) {
            vm.audioFile = audioFile
            vm.context = context
//            qc.uploadAudio(audioFile, context)
            Log.d("RECORDER", "Audio file saved: ${audioFile.absolutePath}")
        } else {
            Log.e("RECORDER", "Audio file does not exist: ${audioFile.absolutePath}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SubmitAnswer(qc: QuestionController,vm: AnswerQuestionViewModel) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission accepted
                Log.d("Audio Request", "PERMISSION GRANTED")
            } else {
                // Permission denied
                Log.d("Audio Request", "PERMISSION DENIED")
            }
        }

    val context = LocalContext.current
    var onRecord = true
    val recorder = AudioRecord(context)

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) -> {
                    recorder.onRecord(onRecord, qc, vm)
                    onRecord = !onRecord
                }

                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }

        }) {
            Text("Record")
        }
    }
}
