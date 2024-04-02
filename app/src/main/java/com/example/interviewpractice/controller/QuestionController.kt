package com.example.interviewpractice.controller

import android.util.Log
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.types.ErrorType
import com.example.interviewpractice.types.Question
import com.example.interviewpractice.types.Tag
import com.example.interviewpractice.types.UIError
import com.example.interviewpractice.types.UserException
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Calendar

class QuestionController(mm: MainModel, am: AuthModel): Controller(mm,am, TAG) {

    fun boost() {
        handler("boost", false) {
            mm.boost()
        }
    }

    fun verifyAndAddNewQuestion(questionText: String, hasVoice: Boolean, hasText: Boolean,
                                tagList: List<Tag>, onSuccess: () -> Unit) {

        handler("verifyQuestion",false) {
            verifyQuestionText(questionText)
            verifyAnswerFormat(hasVoice, hasText)
            verifyTags(tagList)

            val newQuestion = Question(questionText, tagList,
                hasVoice, hasText, false,
                am.getUserID(), getCurrDate(), mutableListOf())
            mm.addQuestion(newQuestion)

            Log.d(TAG, "verifyQuestion:success")
            onSuccess()
        }

    }

//    fun dummyData() {
//        handler("dummyData",false) {
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
//            Log.d(TAG,"dummyData:success")
//        }
//    }

    fun search(queryText: String, filters: Set<Tag> = emptySet()) {
        handler("search",false) {
            mm.searchQuestion(queryText,filters)
            Log.d(TAG,"search:success")
        }
    }

    fun searchBestQuestions() {
        handler("best questions") {
            mm.searchUserQuestion()
            Log.d(TAG,"bestquestions:success")
        }
    }

    private fun handler(functionName: String, func: suspend () -> Unit) {
        MainScope().launch {
            try {
                am.loading = true
                func()
            } catch (ex: FirebaseFirestoreException) {
                //Represents user errors that are caught by Firestore
                am.error = UIError(ex.message!!, ErrorType.USER)
                Log.w(TAG, "$functionName:userException --> ${ex.message}")
            } catch (ex: FirebaseException) {
                //Represents all remaining (system) errors that are caught by Firebase
                am.error = UIError(ex.message!!, ErrorType.SYSTEM)
                Log.e(TAG, "$functionName:systemException --> ${ex.message}")
            } catch (ex: UserException) {
                //Represents all user errors that are caught by us
                am.error = UIError(ex.message!!, ErrorType.USER)
                Log.w(TAG, "$functionName:userException -> ${ex.message}")
            } catch (ex: Exception) {
                //Represents all remaining errors that weren't caught by Firebase or us
                //If we reach here, something very bad has happened
                am.error = UIError(ex.toString(), ErrorType.CATASTROPHIC)
                Log.wtf(TAG, "$functionName:catastrophicFailure", ex)

            } finally {
                //Stop loading after we finished our task
                am.loading = false
            }
        }
    }

    private fun verifyQuestionText(questionText: String) {
        if (questionText.isEmpty()) {
            throw UserException("Question text is empty")
        }
    }

    private fun verifyAnswerFormat(hasVoice: Boolean, hasText: Boolean) {
        if (!hasText && !hasVoice) {
            throw UserException("Must select at least one answer format")
        }
    }

    private fun verifyTags(tagList: List<Tag>) {
        if (tagList.isEmpty()) {
            throw UserException("Must select at least one tag")
        }
    }

    private fun getCurrDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$dayOfMonth"
    }

    companion object {
        private const val TAG = "QuestionController"
    }
}