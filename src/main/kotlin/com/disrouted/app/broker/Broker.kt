package com.disrouted.app.broker

import com.disrouted.app.kernel.Message
import com.disrouted.app.kernel.Startable

interface Broker : Startable {
    fun publish(message: Message)
    fun subscribe(callback: (message: Message) -> Unit)
}