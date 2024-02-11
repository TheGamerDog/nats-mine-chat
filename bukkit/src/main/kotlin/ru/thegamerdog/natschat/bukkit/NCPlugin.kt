package ru.thegamerdog.natschat.bukkit

import ru.thegamerdog.natschat.bukkit.commands.NatsCommand
import ru.thegamerdog.natschat.bukkit.listeners.ChatListener
import ru.thegamerdog.natschat.common.AbstractNCPlugin
import ru.thegamerdog.natschat.common.config.Config
import ru.thegamerdog.natschat.bukkit.listeners.JoinListener
import java.util.logging.Logger

class NCPlugin(
    private val bootstrap: NCBootstrap
) : AbstractNCPlugin() {
    val playerFactory = PlayerFactory()

    override fun startNats() {
        config = Config(bootstrap.dataFolder.toPath().resolve("config.yml"))

        val pluginManager = bootstrap.server.pluginManager
        pluginManager.registerEvents(JoinListener(this), bootstrap)
        pluginManager.registerEvents(ChatListener(this), bootstrap)

        val command = NatsCommand(this)
        bootstrap.getCommand("nats")!!.setExecutor(command)
        bootstrap.getCommand("nats")!!.tabCompleter = command

        getLogger().info("NatsChat plugin initializing")
        super.startNats()
    }

    override fun getLogger(): Logger {
        return bootstrap.logger
    }
}