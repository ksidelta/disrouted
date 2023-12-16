package com.disrouted.app.discord

import com.disrouted.app.kernel.Message
import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.runBlocking
import java.net.URL
import java.util.concurrent.ScheduledFuture
import javax.imageio.ImageIO

class SimpleDiscordBot(val discordToken: String, val channel: String) : DiscordBot {
    val subscribers: MutableList<(message: Message) -> Unit> = mutableListOf()

    override fun onMessage(callback: (Message) -> Unit) {
        subscribers.add(callback)
    }

    override fun start(): Unit = runBlocking {
        val kord = Kord(discordToken)

        kord.on<MessageCreateEvent> {
            println("WIADOMOŚĆ")

            if (
                !message.attachments.isEmpty() &&
                message.author?.isBot != true &&
                message.attachments.first().isImage
            ) {
                val message = Message(
                    message.id,
                    message.content,
                    message.author?.username ?: "unknown",
                    ImageIO.read(URL(message.attachments.first().data.url))
                )

                subscribers.forEach {
                    it(message)
                }
            }
        }

        kord.login()
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
}