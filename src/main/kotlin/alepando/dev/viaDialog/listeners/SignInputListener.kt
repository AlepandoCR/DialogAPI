package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.packets.PacketBuilder
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.Translator.toInputValueList
import alepando.dev.viaDialog.inventory.DialogInventory
import io.papermc.paper.event.packet.UncheckedSignChangeEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class SignInputListener(
    private val dynamicListener: DynamicListener,
    private val list: MutableList<Input<*>>,
    private val button: Button,
    private val dialog: Dialog,
    private val dialogInventory: DialogInventory,
    private val input: Input<*>,
    private val player: Player
) : Listener {

    @EventHandler
    fun onSignChange(event: UncheckedSignChangeEvent) {
        if (event.player != player) return

        val combinedLines = event.lines().joinToString("\n").trim()
        dialogInventory.inputResponses[input.key] = combinedLines

        if (list.isEmpty()) {
            button.action.ifPresent { action ->
                val packet = PacketBuilder.build(action.resourceLocation,dialogInventory.inputResponses.toInputValueList())
                PlayerDialogInteractionEvent(player,packet).callEvent()
            }
        } else {
            dialogInventory.handleInputsSequentially(event.player, dialog, list, button)
        }

        dynamicListener.stop()
    }
}
