package com.disrouted.app.broker.scheduler

import com.disrouted.app.kernel.Message
import com.disrouted.app.kernel.Startable

interface MessageScheduler : Startable {
    fun subscribe(callback: (message: Message) -> Unit)
}