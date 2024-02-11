package ru.thegamerdog.natschat.common.player

import net.kyori.adventure.text.Component
import ru.thegamerdog.natschat.common.AbstractNCPlugin
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

class NatsChatPlayer(
    private val player: Player,
    private val plugin: AbstractNCPlugin
) {
    var currentChannel: String? = null

    fun sendMessageToLocal(message: Component) {
        player.sendMessage(message)
    }

    fun sendMessageToChannel(text: String) {
        if (currentChannel === null) {
            plugin.getLogger().severe("The user is not subscribed to the channel")
            return
        }

        val data = ByteArrayOutputStream()
        val buffer = DataOutputStream(data)

        // TODO:
        //  add prefix/suffix
        //  check text for color
        buffer.writeUTF(player.getDisplayName())
        buffer.writeUTF(text)

        plugin.getLogger().config("Player ${player.getDisplayName()} send message to $currentChannel: $text")
        plugin.natsService.sendMessageToChannel(currentChannel!!, data.toByteArray())
    }
}