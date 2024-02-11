package ru.thegamerdog.natschat.bukkit

import org.bukkit.plugin.java.JavaPlugin

class NCBootstrap: JavaPlugin() {
    private val plugin: NCPlugin = NCPlugin(this)

    override fun onEnable() {
        plugin.startNats()
    }

    override fun onDisable() {
        plugin.stopNats()
    }
}