package ru.thegamerdog.natschat.common.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.BufferedOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.readText

class Config(
    private val configFile: Path
) {
    val configData: ConfigData

    init {
        if (!Files.exists(configFile)) createConfig()

        configData = Yaml.default.decodeFromString(configFile.readText())
    }

    private fun createConfig() {
        val parent = configFile.parent
        if (!Files.exists(configFile) && parent != null) {
            Files.createDirectories(parent)
            Files.createFile(configFile)
        }

        val writer = PrintWriter(
            OutputStreamWriter(
                BufferedOutputStream(Files.newOutputStream(configFile)),
                StandardCharsets.UTF_8
            )
        )

        writer.write(this::class.java.getResource("/config.yml")!!.readText())
        writer.close()
    }

    private fun rewriteConfig() {
        val parent = configFile.parent
        if (!Files.exists(configFile) && parent != null) {
            Files.createDirectories(parent)
            Files.createFile(configFile)
        }

        val writer = PrintWriter(
            OutputStreamWriter(
                BufferedOutputStream(Files.newOutputStream(configFile)),
                StandardCharsets.UTF_8
            )
        )

        writer.write(Yaml.default.encodeToString(configData))
        writer.close()
    }
}