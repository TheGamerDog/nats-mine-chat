package ru.thegamerdog.natschat.bukkit.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import ru.thegamerdog.natschat.bukkit.NCPlugin
import ru.thegamerdog.natschat.common.constants.Commands
import ru.thegamerdog.natschat.common.constants.Strings
import ru.thegamerdog.natschat.common.constants.Permissions

class NatsCommand(
    private val plugin: NCPlugin
): CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        when (args.getOrNull(0)) {
            Commands.JOIN -> onJoin(sender, args.filterIndexed { i, _ -> i != 0 }.toTypedArray())
            Commands.LEAVE -> onLeave(sender, args.filterIndexed { i, _ -> i != 0 }.toTypedArray())
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size <= 1) {
            val completions: MutableList<String> = arrayListOf()

            if (sender.hasPermission(Permissions.JOIN)) {
                completions.add("join")
                completions.add("leave")
            }

            return completions
        }

        return null
    }

    // TODO: Help command
    private fun onHelp(sender: CommandSender, args: Array<out String>) {}

    // TODO: Check if the channel exists
    private fun onJoin(sender: CommandSender, args: Array<out String>) {
        if (sender !is Player) return sender.sendMessage(Strings.DONT_USE_CMD)
        if (!sender.hasPermission(Permissions.JOIN)) return sender.sendMessage(Strings.NOT_PERMISSIONS)
        if (args.isEmpty()) return sender.sendMessage(Strings.NOT_ARGS)

        val natsChatPlayer = plugin.natsChatPlayers[sender.uniqueId]
        natsChatPlayer!!.currentChannel = args[0]
    }

    private fun onLeave(sender: CommandSender, args: Array<out String>) {
        if (sender !is Player) return sender.sendMessage(Strings.DONT_USE_CMD)
        if (!sender.hasPermission(Permissions.JOIN)) return sender.sendMessage(Strings.DONT_USE_CMD)

        val natsChatPlayer = plugin.natsChatPlayers[sender.uniqueId]
        natsChatPlayer!!.currentChannel = null
    }
}