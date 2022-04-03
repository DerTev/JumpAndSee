package de.dertev.jumpandsee

import de.dertev.jumpandsee.ext.bukkit.generateRandomBlockInJumpDistance
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.literal
import net.axay.kspigot.commands.runs
import org.bukkit.Bukkit
import org.bukkit.entity.Player

val settingsCmd = command("jumpandsee") {
    runs {
        if (sender.bukkitSender !is Player) {
            sender.bukkitSender.sendMessage("§cYou must be a Player to do that!")
            return@runs
        }
        val player = sender.bukkitSender as Player

        val randomBlock = player.location.generateRandomBlockInJumpDistance()
        randomBlock.y--

        Game(player.uniqueId, player.location, randomBlock)
    }

    literal("reload") {
        runs {
            try {
                JumpAndSee.questions = Json.decodeFromString(JumpAndSee.questionsFile.readText())
            } catch(e: Exception) {
                Bukkit.getLogger().info("Error while loading questions.json!")
                sender.bukkitSender.sendMessage("§cError while loading questions.json!")
            }
        }
    }
}