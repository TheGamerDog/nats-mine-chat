package ru.thegamerdog.natschat.bukkit

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import ru.thegamerdog.natschat.common.player.AbstractPlayerFactory
import java.util.*

class PlayerFactory : AbstractPlayerFactory<Player>() {
    override fun getUUID(player: Player): UUID {
        return player.uniqueId
    }

    override fun getDisplayName(player: Player): String {
        return player.displayName
    }

    override fun sendMessage(player: Player, message: Component) {
        player.sendMessage(message)
    }
}