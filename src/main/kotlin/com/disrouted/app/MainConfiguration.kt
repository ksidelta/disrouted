package com.disrouted.app

import com.disrouted.app.broker.Broker
import com.disrouted.app.broker.BrokerImpl
import com.disrouted.app.broker.queue.InMemoryQueue
import com.disrouted.app.broker.scheduler.SpringScheduler
import com.disrouted.app.discord.DiscordBot
import com.disrouted.app.discord.SimpleDiscordBot
import com.disrouted.app.kernel.Message
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler


@Configuration
class MainConfiguration {
    @Bean
    fun broker(taskScheduler: TaskScheduler): Broker {
        val queue = InMemoryQueue()
        val broker = BrokerImpl(queue, SpringScheduler(queue, taskScheduler, "*/10 * * * * *"))

        return broker
    }

    @Bean
    fun bot(@Value("\${discord.token}") token: String): DiscordBot {
        val bot = SimpleDiscordBot(token, "bots")
        return bot
    }

    fun module(
        broker: Broker,
        bot: DiscordBot
    ) {
        bot.onMessage { broker.publish(Message(it.id, it.content, it.author, null)) }


    }

    @Bean
    fun runner(
        broker: Broker,
        bot: DiscordBot
    ): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            println("Running")

            bot.onMessage { broker.publish(Message(it.id, it.content, it.author, null)) }

            broker.start()
            bot.start()
        }
    }
}