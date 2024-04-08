package com.example.interviewpractice.controller

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.example.interviewpractice.helpers.getCurrentDate
import com.example.interviewpractice.helpers.verifyGenericString
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.AnsweredQuestion
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.FetchType
import com.example.interviewpractice.types.Message
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Review
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.Calendar
import java.util.UUID

class QuestionController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun boost() {
        handler("boost", false) {
            mm.boost()
        }
    }

    fun verifyAndAddNewQuestion(questionText: String,
                                tagList: List<Tag>, onSuccess: () -> Unit) {

        handler("verifyQuestion",false) {
            verifyQuestionText(questionText)
            verifyTags(tagList)

            val questionID = UUID.randomUUID().toString()

            val newQuestion = Question(questionText=questionText, tags = tagList,
                userID = am.getUserID(), date = getCurrentDate(), answers = mutableListOf(), questionID = questionID)
            mm.addQuestion(newQuestion)

            Log.d(TAG, "verifyQuestion:success")

            onSuccess()
            am.error = UIError("Successfully created question",ErrorType.INFO)
        }
    }

    fun verifySubmitAnswer(answerText:String,questionID: String, audioFile: File?, context: Context?, tags: List<Tag>, questionText: String, goToHome: ()->Unit) {
        handler("verifySubmitAnswer") {
            var fileUri: String = ""
            if (audioFile == null || context == null) {
                throw UserException("No audio submitted but question which requires it")
            }
            fileUri = FileProvider.getUriForFile(context, context.packageName + ".provider", audioFile).toString()

            val audioLength = getAudioFileDuration(audioFile.absolutePath)
            val uid = am.getUserID()

            val question = AnsweredQuestion(
                userID = uid,
                questionID = questionID,
                date = getCurrentDate(),
                downloadUrl = "",
                audioTime = audioLength,
                tags = tags,
                questionText = questionText
            )

            mm.addAnsweredQuestion(question, fileUri)
            am.loading += 1
            am.isInit = true
            goToHome()
            am.error = UIError("Question submitted successfully", errorType = ErrorType.INFO)
        }

    }

    private fun getAudioFileDuration(filePath: String): Int {
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(filePath) // Set the data source of the audio file
            mediaPlayer.prepare() // Prepare the MediaPlayer
            val duration = mediaPlayer.duration // Get the duration of the audio file in milliseconds
            mediaPlayer.release() // Release the MediaPlayer
            return duration
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(TAG,"ERROR IN GETTING AUDIO DURATION")
        return 0 // Return -1 if there was an error
    }

    fun loadNextQuestion(question: Question) {
        mm.currentQuestionData = question
        Log.d(TAG, "LOADED NEXT QUESTION: $question")
    }


    fun search(queryText: String, completed: Boolean, filters: List<Tag> = emptyList()) {
        handler("search",false) {
            mm.searchQuestion(queryText, am.getUserID(), filters, completed = completed)
            Log.d(TAG,"search:success")
        }
    }

    fun searchBestQuestions() {
        handler("best questions") {
            mm.searchUserQuestion()
            Log.d(TAG,"bestquestions:success")
        }
    }


    private fun verifyQuestionText(questionText: String) {
        if (questionText.isEmpty()) {
            throw UserException("Question text is empty")
        }
    }

    private fun verifyTags(tagList: List<Tag>) {
        if (tagList.isEmpty()) {
            throw UserException("Must select at least one tag")
        }
    }

    companion object {
        private const val TAG = "QuestionController"
    }
}