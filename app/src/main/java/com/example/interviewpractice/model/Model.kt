package com.example.interviewpractice.model

import com.example.interviewpractice.user_interface.ModelSubscriber

class Model {
    private val subscribers = mutableListOf<ModelSubscriber>()
    fun notifySubscribers() {
        subscribers.forEach() {
            it.update()
        }
    }

    fun subscribe(subscriber: ModelSubscriber) { subscribers.add(subscriber) }
    fun unsubscribe(subscriber: ModelSubscriber) { subscribers.remove(subscriber) }
}