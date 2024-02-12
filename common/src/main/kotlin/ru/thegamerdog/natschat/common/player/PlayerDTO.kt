package ru.thegamerdog.natschat.common.player

import net.kyori.adventure.text.Component
import java.util.*

class PlayerDTO<T>(
    private val factory: AbstractPlayerFactory<T>,
    private val player: T
) : Player {
    private val uuid: UUID = factory.getUUID(player)
    private val name: String = factory.getDisplayName(player)

    override fun getUUID(): UUID {
        return uuid
    }

    override fun getDisplayName(): String {
        return name
    }

    override fun sendMessage(msg: Component) {
        factory.sendMessage(player, msg)
    }
}