package ru.thegamerdog.natschat.common.listener

import ru.thegamerdog.natschat.common.AbstractNCPlugin
import ru.thegamerdog.natschat.common.player.NatsChatPlayer
import ru.thegamerdog.natschat.common.player.Player
import java.util.*

abstract class AbstractJoinListener<T : AbstractNCPlugin>(private val plugin: T) {
    protected fun onJoin(player: Player) {
        plugin.natsChatPlayers.putIfAbsent(player.getUUID(), NatsChatPlayer(player, plugin))
    }

    protected fun onLeave(uuid: UUID) {
        plugin.natsChatPlayers.remove(uuid)
    }
}