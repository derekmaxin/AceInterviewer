package com.example.interviewpractice.helpers

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

val EH = CoroutineExceptionHandler { _, throwable ->
    Log.d("EH","CAUGHT EXCEPTION")
    throw throwable
}