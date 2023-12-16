package com.disrouted.app.kernel

import dev.kord.common.entity.Snowflake
import java.awt.image.BufferedImage

data class Message(
    val id: Snowflake,
    val content: String,
    val author: String,
    val image: BufferedImage?
)
