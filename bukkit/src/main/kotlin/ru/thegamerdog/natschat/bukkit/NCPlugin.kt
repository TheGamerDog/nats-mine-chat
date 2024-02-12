package ru.thegamerdog.natschat.bukkit

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import ru.thegamerdog.natschat.bukkit.commands.NatsCommand
import ru.thegamerdog.natschat.bukkit.listeners.ChatListener
import ru.thegamerdog.natschat.bukkit.listeners.JoinListener
import ru.thegamerdog.natschat.common.AbstractNCPlugin
import ru.thegamerdog.natschat.common.config.Config
import java.util.logging.Logger

class NCPlugin(
    private val bootstrap: NCBootstrap
) : AbstractNCPlugin() {
    val playerFactory = PlayerFactory(this)
    var audiences: BukkitAudiences = BukkitAudiences.create(bootstrap)

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