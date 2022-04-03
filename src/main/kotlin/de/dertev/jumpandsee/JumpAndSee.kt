package de.dertev.jumpandsee

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.axay.kspigot.commands.register
import net.axay.kspigot.languageextensions.kotlinextensions.createIfNotExists
import net.axay.kspigot.main.KSpigot
import java.io.File

class JumpAndSee : KSpigot() {
    companion object {
        var questions: List<Question> = listOf()
        val questionsFile = File("plugins/JumpAndSee/questions.json")
    }

    override fun startup() {
        questionsFile.createIfNotExists()

        try {
            questions = Json.decodeFromString(questionsFile.readText())
        } catch(e: Exception) {
            logger.info("Error while loading questions.json!")
        }

        settingsCmd.register()
        logger.info("§aThe Plugin was enabled!")
    }

    override fun shutdown() {
        logger.info("§cThe Plugin was disabled!")
    }
}
