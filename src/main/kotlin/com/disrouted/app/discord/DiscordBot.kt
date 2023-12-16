package com.disrouted.app.discord

import com.disrouted.app.kernel.Message
import com.disrouted.app.kernel.Startable

interface DiscordBot : Startable {
    fun onMessage(callback: (Message) -> Unit)
}