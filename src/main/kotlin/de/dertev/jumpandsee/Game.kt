package de.dertev.jumpandsee

import de.dertev.jumpandsee.JumpAndSee.Companion.questions
import de.dertev.jumpandsee.ext.bukkit.generateRandomBlockInJumpDistance
import de.dertev.jumpandsee.ext.bukkit.isEqualsToIgnoreY
import net.axay.kspigot.chat.input.awaitChatInput
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.axay.kspigot.event.unregister
import net.axay.kspigot.extensions.bukkit.toLegacyString
import net.axay.kspigot.items.itemStack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import java.util.UUID

class Game(
    private val player: UUID,
    private var currentBlock: Location,
    private var nextBlock: Location
) {
    private var waitForAnswer = false

    private val moveListener = listen<PlayerMoveEvent> {
        if (it.player.uniqueId != player) return@listen
        if (!it.player.isOnGround) return@listen

        if (waitForAnswer) {
            getPlayer().sendMessage("§cPlease answer the question before move!")
            it.isCancelled = true
            return@listen
        }

        if (it.player.location.isEqualsToIgnoreY(nextBlock)) {
            val question = questions.random()
            waitForAnswer = true
            getPlayer().awaitChatInput(question.question, timeoutSeconds = 600) {
                waitForAnswer = false
                if (it.input!!.toLegacyString().lowercase() in question.answers) {
                    getPlayer().sendMessage("§aRight!")
                    getPlayer().inventory.addItem(itemStack(Material.DIAMOND){})
                }
                else stopGame()
            }

            currentBlock.block.type = Material.AIR

            currentBlock = nextBlock
            nextBlock = nextBlock.generateRandomBlockInJumpDistance()
            registerNextBlock()

            currentBlock
        } else if (!it.player.location.isEqualsToIgnoreY(currentBlock)) {
            stopGame()
        }
    }

    private val deathListener = listen<PlayerDeathEvent> {
        if (it.player.uniqueId != player) return@listen
        stopGame()
    }

    init {
        moveListener.register()
        deathListener.register()
        registerNextBlock()
    }


    private fun stopGame() {
        getPlayer().sendMessage("§cYou missed the game!")
        moveListener.unregister()
        deathListener.unregister()
        currentBlock.block.type = Material.AIR
        nextBlock.block.type = Material.AIR
    }

    private fun getPlayer() = Bukkit.getPlayer(player)!!

    private fun registerNextBlock() {
        nextBlock.block.type = listOf(
            Material.BLACK_CONCRETE,
            Material.BLUE_CONCRETE,
            Material.CYAN_CONCRETE,
            Material.GRAY_CONCRETE,
            Material.BROWN_CONCRETE,
            Material.GREEN_CONCRETE,
            Material.LIGHT_BLUE_CONCRETE,
            Material.LIME_CONCRETE,
            Material.MAGENTA_CONCRETE,
            Material.ORANGE_CONCRETE,
            Material.PINK_CONCRETE,
            Material.PURPLE_CONCRETE,
            Material.RED_CONCRETE,
            Material.WHITE_CONCRETE,
            Material.YELLOW_CONCRETE,
        ).random()
    }
}