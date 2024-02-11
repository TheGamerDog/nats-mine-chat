package ru.thegamerdog.natschat.common

import ru.thegamerdog.natschat.common.config.Config
import ru.thegamerdog.natschat.common.player.NatsChatPlayer
import java.util.*
import java.util.logging.Logger


abstract class AbstractNCPlugin {
    lateinit var natsService: NatsService

    val natsChatPlayers: MutableMap<UUID, NatsChatPlayer> = mutableMapOf()
    var config: Config? = null

    open fun startNats() {
        natsService = NatsService(this)
        this.getLogger().info("Nats-communication success started")
    }

    fun stopNats() {
        natsService.closeConnection()
        this.getLogger().info("Nats-communication stopped")
    }

    abstract fun getLogger(): Logger
}