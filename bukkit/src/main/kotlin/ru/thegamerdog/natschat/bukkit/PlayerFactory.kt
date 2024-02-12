package ru.thegamerdog.natschat.bukkit

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import ru.thegamerdog.natschat.common.player.AbstractPlayerFactory
import java.util.*

class PlayerFactory(
    private val plugin: NCPlugin
) : AbstractPlayerFactory<Player>() {
    override fun getUUID(player: Player): UUID {
        return player.uniqueId
    }

    override fun getDisplayName(player: Player): String {
        return player.displayName
    }

    override fun sendMessage(player: Player, message: Component) {
        plugin.audiences.player(player).sendMessage(message)
    }
}