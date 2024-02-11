package ru.thegamerdog.natschat.bukkit.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import ru.thegamerdog.natschat.bukkit.NCPlugin
import ru.thegamerdog.natschat.common.listener.AbstractJoinListener

class JoinListener(
    private val plugin: NCPlugin
): AbstractJoinListener<NCPlugin>(plugin), Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        onJoin(plugin.playerFactory.wrap(event.player))
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        onLeave(event.player.uniqueId)
    }
}