package com.example.interviewpractice.model

import androidx.lifecycle.ViewModel
import com.example.interviewpractice.frontend.Subscriber
import com.example.interviewpractice.frontend.LoaderMMViewModel

abstract class Presenter {
    val subscribers = mutableListOf<Subscriber>()
    fun notifySubscribers() {
        subscribers.forEach() {
            it.update()
            if (it is LoaderMMViewModel) it.updateLoading()

        }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }
}
