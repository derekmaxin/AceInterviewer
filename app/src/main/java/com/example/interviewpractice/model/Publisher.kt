package com.example.interviewpractice.model

import com.example.interviewpractice.frontend.Subscriber

abstract class Presenter {
    private val subscribers = mutableListOf<Subscriber>()
    private val systemDataSubscribers = mutableListOf<Subscriber>()
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

    fun notifySystemSubscribers() {
        systemDataSubscribers.forEach() {
            it.update()
        }
    }
    fun systemDataSubscribe(subscriber: Subscriber) {
        systemDataSubscribers.add(subscriber)
    }
}
