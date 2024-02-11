package ru.thegamerdog.natschat.common.player

import net.kyori.adventure.text.Component
import java.util.UUID

interface Player {
    fun sendMessage(msg: Component)

    fun getDisplayName(): String

    fun getUUID(): UUID
}