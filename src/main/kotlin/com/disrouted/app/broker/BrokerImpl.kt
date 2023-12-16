package com.disrouted.app.broker

import com.disrouted.app.broker.queue.MessageQueue
import com.disrouted.app.broker.scheduler.MessageScheduler
import com.disrouted.app.kernel.Message

class BrokerImpl(val queue: MessageQueue, val scheduler: MessageScheduler) : Broker {
    override fun publish(message: Message) {
        queue.add(message)
    }

    override fun subscribe(callback: (message: Message) -> Unit) {
        scheduler.subscribe(callback)
    }

    override fun start() {
        scheduler.start()
    }

    override fun stop() {
        scheduler.stop()
    }
}