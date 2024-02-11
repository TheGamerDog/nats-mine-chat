package ru.thegamerdog.natschat.common.config

import kotlinx.serialization.Serializable

@Serializable
data class ConfigData(
    val natsIp: String,
    val natsPort: Int,

    val channels: ArrayList<String>
)