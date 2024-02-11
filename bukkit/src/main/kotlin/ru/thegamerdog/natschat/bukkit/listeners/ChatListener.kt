package ru.thegamerdog.natschat.bukkit.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import ru.thegamerdog.natschat.bukkit.NCPlugin
import ru.thegamerdog.natschat.common.constants.Permissions
import ru.thegamerdog.natschat.common.constants.Strings

class ChatListener(
    private val plugin: NCPlugin
): Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChatMessage(event: AsyncPlayerChatEvent) {
        val natsChatPlayer = plugin.natsChatPlayers[event.player.uniqueId]
        if (natsChatPlayer!!.currentChannel === null) return

        event.isCancelled = true

        // TODO: Channels only lock
        //  Detailed channel management system
        if (!event.player.hasPermission(Permissions.SPEAK)) {
            event.player.sendMessage("${Strings.NATS_CHAT_PREFIX} ${Strings.CANT_SPEAK}")
        } else {
            natsChatPlayer!!.sendMessageToChannel(event.message)
        }
    }
}