package com.example.interviewpractice.model

import com.example.interviewpractice.frontend.Subscriber

abstract class Presenter {
    private val subscribers = mutableListOf<Subscriber>()
    fun notifySubscribers() {
        subscribers.forEach() {
            it.update()
        }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }
}
