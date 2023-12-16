package com.disrouted.app.broker.queue

import com.disrouted.app.kernel.Message
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class InMemoryQueue : MessageQueue {
    val queue: Queue<Message> = ConcurrentLinkedQueue()

    override fun add(message: Message) {
        queue.add(message)
    }

    override fun consume(): Message? = queue.poll()
}