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
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class QuestionController(private val mm: MainModel, private val am: AuthModel) {
    fun dummyData() {
        handler("dummyData") {
            val t1 = listOf<Tag>(Tag.ART)
            val t2 = listOf<Tag>(Tag.ART, Tag.BUSINESS)
            val t3 = listOf<Tag>(Tag.CS)
            val t4 = listOf<Tag>(Tag.CHEMISTRY)
            val t5 = listOf<Tag>(Tag.PHYSICS, Tag.MATH, Tag.ENGLISH)
            val q1 = Question("Question 1", t1, false, true, false)
            val q2 = Question("Question 2", t2, false, true, false)
            val q3 = Question("Question 3", t3, false, true, false)
            val q4 = Question("Question 4", t4, false, true, false)
            val q5 = Question("Question 5", t5, false, true, false)
            mm.addQuestion(q1)
            mm.addQuestion(q2)
            mm.addQuestion(q3)
            mm.addQuestion(q4)
            mm.addQuestion(q5)
            Log.d(TAG,"dummyData:success")
        }
    }

    fun search(queryText: String) {
        handler("search") {
            mm.searchQuestion(queryText)
            Log.d(TAG,"search:success")
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
    companion object {
        private const val TAG = "QuestionController"
    }
}