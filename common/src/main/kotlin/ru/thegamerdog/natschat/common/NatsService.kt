package ru.thegamerdog.natschat.common

import io.nats.client.*
import net.kyori.adventure.text.Component
import ru.thegamerdog.natschat.common.config.ConfigData
import ru.thegamerdog.natschat.common.constants.Strings
import java.io.ByteArrayInputStream
import java.io.DataInputStream

class NatsService(private val plugin: AbstractNCPlugin) {
    private val serverURL: String
    private val connection: Connection

    private val dispatcher: Dispatcher
    private val subscriptions: MutableMap<String, Subscription> = mutableMapOf()
    private val configData: ConfigData?

    init {
        if (plugin.config !== null) {
            configData = plugin.config!!.configData
            serverURL = "nats://${configData.natsIp}:${configData.natsPort}"
        } else {
            configData = null
            serverURL = "nats://localhost:4222"
        }

        connection = initConnection()
        dispatcher = connection.createDispatcher()

        initChannels()
    }

    private fun initConnection(): Connection {
        // TODO: Error handler
        val options = Options.Builder()
            .server(serverURL)
            .build()

        return Nats.connect(options)
    }

    private fun initChannels() {
        if (configData === null) return

        for (channelName in configData.channels) {
            subscribeToChannel(channelName)
        }
    }

    private fun subscribeToChannel(channelName: String) {
        val subscription = dispatcher.subscribe(channelName) { msg ->
            plugin.natsChatPlayers.forEach { (_, player) ->
                if (player.currentChannel == channelName) {
                    val buffer = DataInputStream(ByteArrayInputStream(msg.data))

                    // TODO:
                    //  add prefix/suffix
                    //  check text for color
                    val playerName = buffer.readUTF()
                    val text = buffer.readUTF()

                    val chatPrefixComponent = Component.text(Strings.NATS_CHAT_PREFIX).appendSpace()
                    val channelNameComponent = Component.text("| $channelName |").appendSpace()

                    val playerNameComponent = Component.text("$playerName:").appendSpace()
                    val msgComponent = Component.text(text)

                    val finalComponent = Component.text()
                        .append(chatPrefixComponent)
                        .append(channelNameComponent)
                        .append(playerNameComponent)
                        .append(msgComponent)
                        .build()

                    player.sendMessageToLocal(finalComponent)
                }
            }
        }

        if (subscription === null) {
            plugin.getLogger().severe("Error subscribing to $channelName")
        } else {
            subscriptions[channelName] = subscription
        }
    }

    fun closeConnection() {
        connection.closeDispatcher(dispatcher)
        connection.close()
    }

    fun sendMessageToChannel(channelName: String, msg: ByteArray) {
        connection.publish(channelName, msg)
    }
}