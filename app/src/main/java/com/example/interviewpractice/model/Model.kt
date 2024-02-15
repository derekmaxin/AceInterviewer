package com.example.interviewpractice.model

import com.example.interviewpractice.viewmodel.Subscriber

class Model {
    private val subscribers = mutableListOf<Subscriber>()
    fun notifySubscribers() {
        subscribers.forEach() {
            it.update()
        }
    }

    fun subscribe(subscriber: Subscriber) { subscribers.add(subscriber) }
    fun unsubscribe(subscriber: Subscriber) { subscribers.remove(subscriber) }
}