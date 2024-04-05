package com.example.interviewpractice.frontend

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.interviewpractice.model.AuthModel
import com.example.interviewpractice.model.MainModel
import com.example.interviewpractice.model.Presenter

open class MMViewModel(): ViewModel(), Subscriber {

    protected lateinit var model: MainModel

    fun addModel(mm: MainModel) {
        if (!this::model.isInitialized) {
            model = mm
            model.subscribe(this)
        }

    }
    override fun unsubscribe() {
        Log.d("UNSUB", "UNSUBSCRIBED!!")
        model.unsubscribe(this)
    }
    override fun update() {

    }
}

open class AMViewModel(): ViewModel(), Subscriber {
    protected lateinit var m: AuthModel

    fun addModel(am: AuthModel) {
        if (!this::m.isInitialized) {
            m = am
            m.subscribe(this)
        }
    }
    override fun update() {

    }
    override fun unsubscribe() {
        Log.d("UNSUB", "UNSUBSCRIBED!!")
        m.unsubscribe(this)
    }
}

open class LoaderMMViewModel(): ViewModel(), Subscriber {
    var localLoading by mutableStateOf<Boolean>(false)
    protected lateinit var model: MainModel

    fun addModel(mm: MainModel) {
        model = mm
        if (!model.subscribers.contains(this)) {
            Log.d("PROFILE.KT ","Contains? ${mm.subscribers.contains(this)}")
            model.subscribe(this)
            Log.d("PROFILE.KT ","Contains? ${mm.subscribers.contains(this)}")
        }
    }
    fun updateLoading() {
        if (localLoading != model.localLoading) {
            localLoading = model.localLoading

        }
    }
    override fun unsubscribe() {
        Log.d("UNSUB", "UNSUBSCRIBED!!")
        model.unsubscribe(this)
    }

    override fun update() {

    }
}

interface Subscriber {
    fun update()
    fun unsubscribe()
}
