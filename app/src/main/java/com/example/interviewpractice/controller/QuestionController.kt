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

            var mediaPlayer = MediaPlayer()
            var audioLength: Int

            mediaPlayer.let {
                if (it.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                }
            }
            mediaPlayer.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(fileUri)
                prepare()
            }

            audioLength = mediaPlayer.duration
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

    fun loadNextQuestion(question: Question) {
        mm.currentQuestionData = question
        Log.d(TAG, "LOADED NEXT QUESTION: $question")
    }

    fun dummyData() {
        handler("dummyData",false) {
//            val t1 = listOf<Tag>(Tag.ART)
//            val t2 = listOf<Tag>(Tag.ART, Tag.BUSINESS)
//            val t3 = listOf<Tag>(Tag.CS)
//            val t4 = listOf<Tag>(Tag.CHEMISTRY)
//            val t5 = listOf<Tag>(Tag.PHYSICS, Tag.MATH)
//            val q1 = Question("What is the rule of thirds?", t1, false, true, false, "randomUser")
//            val q2 = Question("Name the three most influential art galleries", t2, false, true, false, "randomUser")
//            val q3 = Question("Describe MVVM architecture", t3, false, true, false, "randomUser")
//            val q4 = Question("Name the 20 essential amino acids", t4, false, true, false, "randomUser")
//            val q5 = Question("Describe how the Fourier transform works", t5, false, true, false, "randomUser")
//            mm.addQuestion(q1)
//            mm.addQuestion(q2)
//            mm.addQuestion(q3)
//            mm.addQuestion(q4)
//            mm.addQuestion(q5)



//            val answered1: AnsweredQuestion = AnsweredQuestion(
//                am.getUserID(),
//                "Blah",
//                "",
//                0,
//                false,
//                questionID = "",
//                getCurrentDate()
//
//            )
//            val answered2: AnsweredQuestion = AnsweredQuestion(
//                am.getUserID(),
//                "Blah2",
//                "",
//                0,
//                false,
//                questionID = "",
//                getCurrentDate()
//
//            )
//
//            mm.addAnsweredQuestion(answered1)
//            mm.addAnsweredQuestion(answered2)
//
//            val review1: Review = Review(
//                am.getUserID(),
//                "ID2",
//                am.getUserID(),
//                "HORRIBLE ANSWER",
//                1,
//                1,
//                getCurrentDate()
//
//            )
//            val review2: Review = Review(
//                am.getUserID(),
//                "ID2",
//                am.getUserID(),
//                "AWESOME ANSWER",
//                5,
//                4,
//                getCurrentDate()
//
//            )

//            mm.addReview(review1)
//            mm.addReview(review2)
            val month = 3
            val year = 2023
            val firstDay = Calendar.getInstance()
            firstDay.set(Calendar.MONTH, month)
            firstDay.set(Calendar.YEAR, year)
            firstDay.set(Calendar.DATE, 1)

            val lastDay = Calendar.getInstance()
            lastDay.set(Calendar.MONTH, month)
            lastDay.set(Calendar.YEAR, year)
            lastDay.set(Calendar.DATE, lastDay.getActualMaximum(Calendar.DATE))
            mm.invalidate(FetchType.HISTORY)
            mm.getHistoryData(firstDay.time,lastDay.time, am.getUserID())

            Log.d(TAG,"dummyData:success")
        }
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

//    private fun verifyAnswerFormat(hasVoice: Boolean, hasText: Boolean) {
//        if (!hasText && !hasVoice) {
//            throw UserException("Must select at least one answer format")
//        }
//    }

    private fun verifyTags(tagList: List<Tag>) {
        if (tagList.isEmpty()) {
            throw UserException("Must select at least one tag")
        }
    }

//    fun uploadAudio(audioFile : File, context: Context){
//        handler("uploadAudio",false) {
//            mm.uploadAudio(audioFile, context)
//        }
//    }
//    private fun getCurrDate(): String {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH) + 1
//        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
//        return "$year-$month-$dayOfMonth"
//    }

    companion object {
        private const val TAG = "QuestionController"
    }
}