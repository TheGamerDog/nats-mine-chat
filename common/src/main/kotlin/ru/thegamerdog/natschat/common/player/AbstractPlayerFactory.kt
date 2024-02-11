package ru.thegamerdog.natschat.common.player

import net.kyori.adventure.text.Component
import java.util.*


abstract class AbstractPlayerFactory<T> {
    abstract fun getUUID(player: T): UUID
    abstract fun getDisplayName(player: T): String
    abstract fun sendMessage(player: T, message: Component)

    fun wrap(player: T): Player {
        return PlayerDTO(this, player)
    }
}