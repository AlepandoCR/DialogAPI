package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.packets.PacketBuilder
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.Translator.toInputValueList
import alepando.dev.viaDialog.inventory.DialogInventory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class AnvilInputListener(
    private val dynamicListener: DynamicListener,
    private val list: MutableList<Input<*>>,
    private val button: Button,
    private val dialog: Dialog,
    private val dialogInventory: DialogInventory,
    private val input: Input<*>,
    private val player: Player
) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.type != InventoryType.ANVIL) return
        if (event.whoClicked != player) return
        if (event.rawSlot != 2) return

        event.isCancelled = true

        val resultItem = event.inventory.getItem(2)
        if(resultItem == null){
            DialogAPI.log("Item null on anvil")
            return
        }
        if (!resultItem.hasItemMeta()) return

        val renamed = resultItem.itemMeta.displayName
        if (renamed.isEmpty()) return

        dialogInventory.inputResponses[input.key] = renamed

        if (list.isEmpty()) {
            button.action.ifPresent { action ->
                val packet = PacketBuilder.build(action.resourceLocation, dialogInventory.inputResponses.toInputValueList())
                PlayerDialogInteractionEvent(player, packet).callEvent()
            }
        } else {
            dialogInventory.handleInputsSequentially(player, dialog, list, button)
        }

        player.closeInventory()
        dynamicListener.stop()
    }
}
