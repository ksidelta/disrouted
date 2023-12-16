package com.disrouted.app.broker.queue

import com.disrouted.app.kernel.Message

interface MessageQueue {
    fun add(message: Message)
    fun consume(): Message?
}