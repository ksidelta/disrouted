package com.disrouted.app.broker.scheduler

import com.disrouted.app.broker.queue.MessageQueue
import com.disrouted.app.kernel.Message
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import java.util.concurrent.ScheduledFuture

class SpringScheduler(
    val messageQueue: MessageQueue,
    val taskScheduler: TaskScheduler,
    val cronExpression: String
) : MessageScheduler {
    val subscribers: MutableList<(message: Message) -> Unit> = mutableListOf()
    var job: ScheduledFuture<Any>? = null

    override fun subscribe(callback: (message: Message) -> Unit) {
        subscribers.add(callback)
    }

    override fun start() {
        taskScheduler.schedule({ fire() }, CronTrigger(cronExpression))
    }

    override fun stop() {
        job?.cancel(true)
    }

    private fun fire() {
        messageQueue.consume()?.let { message ->
            subscribers.forEach { it(message) }
        }
    }

}