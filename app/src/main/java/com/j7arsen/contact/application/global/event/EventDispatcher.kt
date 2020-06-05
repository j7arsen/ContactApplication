package com.j7arsen.contact.application.global.event

import java.util.*
import kotlin.collections.ArrayList

class EventDispatcher {

    private val eventListeners = HashMap<EventType, MutableList<EventListener>>()

    fun addEventListener(vararg eventType: EventType, listener: EventListener): EventListener {
        eventType.forEach {
            if (eventListeners[it] == null) {
                eventListeners[it] = ArrayList()
            }

            val list = eventListeners[it]
            list?.add(listener)
        }

        return listener
    }

    fun removeEventListener(listener: EventListener) = eventListeners
        .filter { it.value.size > 0 }
        .forEach { it.value.remove(listener) }

    fun sendEvent(eventType: EventType, data: Any? = null) {
        eventListeners
            .filter { it.key == eventType && it.value.size > 0 }
            .forEach {
                it.value.forEach { listener -> listener.onEvent(eventType, data) }
            }
    }

    interface EventListener {
        fun onEvent(eventType: EventType, data: Any?)
    }
}