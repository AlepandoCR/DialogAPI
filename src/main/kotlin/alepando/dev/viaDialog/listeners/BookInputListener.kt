package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.packets.PacketBuilder
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.InputValue
import alepando.dev.dialogapi.util.InputValueList
import alepando.dev.viaDialog.inventory.DialogInventory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEditBookEvent

class BookInputListener(
    private val player: Player,
    private val dynamicListener: DynamicListener,
    private val list: MutableList<Input<*>>,
    private val button: Button,
    private val inventory: DialogInventory,
    private val input: Input<*>,
    private val dialog: Dialog
) : Listener {

    @EventHandler
    fun onBookEdit(event: PlayerEditBookEvent) {
        if (event.player != player) return
        if (!event.isSigning) return

        val bookMeta = event.newBookMeta
        val content = bookMeta.pages().joinToString("\n")
        inventory.inputResponses[input.key] = content

        if (list.isEmpty()) {
            val resourceLocation = button.action
                .map { it.resourceLocation }
                .orElse(ResourceLocation("inventory_gui", "no_button"))

            val inputValues = InputValueList()
            inventory.inputResponses.forEach { (key, value) ->
                inputValues.add(InputValue(key, value))
            }

            val packet = PacketBuilder.build(resourceLocation, inputValues)
            PlayerDialogInteractionEvent(player, packet, DialogAPI.plugin!!).callEvent()
        }

        dynamicListener.stop()
        inventory.handleInputsSequentially(player, dialog, list, button)
    }
}
