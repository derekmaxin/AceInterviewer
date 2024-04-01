package com.example.interviewpractice.frontend.views.submitanswer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

class AudioRecord(private val context: Context) {

    private var fileName: String = "test"
    private var recorder: MediaRecorder? = null
    private var audioDir: File = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "AudioMemos")
    private var audioDirPath = audioDir.absolutePath
    private var recordingFile = File("$audioDirPath/$fileName.m4a")

    init {
        audioDir.mkdirs()
        Log.d("RECORDER", "Recording file location: $audioDirPath")
        Log.d("RECORDER", "File location: ${recordingFile.absolutePath}")

    }

    fun onRecord(start: Boolean) = if (start) {
        Log.d("RECORDER", "Started Recording")
        startRecording()
    } else {
        Log.d("RECORDER", "Stopped Recording")
        stopRecording()
    }

    private fun startRecording() {
        recorder = MediaRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(recordingFile.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("RECORDER", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

}


@Composable
@Preview
fun SubmitAnswer() {
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
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
        Button( onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) -> {
                    recorder.onRecord(onRecord)
                    onRecord = !onRecord
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }

        } ) {
            Text("Record")
        }
    }



}