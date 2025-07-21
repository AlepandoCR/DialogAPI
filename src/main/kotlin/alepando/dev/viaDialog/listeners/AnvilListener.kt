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
import alepando.dev.dialogapi.util.Translator.toPlainText
import alepando.dev.viaDialog.inventory.DialogInventory
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.PrepareAnvilEvent

class AnvilListener(
    private val player: Player,
    private val dynamicListener: DynamicListener,
    private val list: MutableList<Input<*>>,
    private val button: Button,
    private val inventory: DialogInventory,
    private val input: Input<*>,
    private val dialog: Dialog
) : Listener {

    private var capturedRename: String? = null

    @EventHandler
    fun onPrepareAnvil(event: PrepareAnvilEvent) {
        if (event.view.player != player) return

        val inputItem = event.inventory.getItem(0) ?: return
        val meta = inputItem.itemMeta ?: return
        val renameText = meta.displayName ?: return

        if (renameText.isEmpty()) return

        // Forzamos el resultado en el slot 2 del anvil
        val resultItem = inputItem.clone()
        val resultMeta = resultItem.itemMeta
        resultMeta.setDisplayName(renameText)
        resultItem.itemMeta = resultMeta

        event.result = resultItem
        capturedRename = renameText
        DialogAPI.log("[PrepareAnvilEvent] captured rename: $renameText")
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.type != InventoryType.ANVIL) return
        if (event.whoClicked !is Player) return
        if (event.whoClicked != player) return
        if (event.rawSlot != 2) return

        event.isCancelled = true

        Bukkit.getScheduler().runTaskLater(DialogAPI.plugin!!, Runnable {
            val rename = capturedRename

            if (rename.isNullOrEmpty()) {
                DialogAPI.log("[InputProcessing] captured rename is null or empty")
                return@Runnable
            }

            val text = Component.text(rename)
            inventory.inputResponses[input.key] = text.toPlainText()

            if (list.isEmpty()) {
                DialogAPI.log("[RemainingInputs] no inputs left")

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

            player.closeInventory()
            dynamicListener.stop()
            inventory.handleInputsSequentially(player, dialog, list, button)
        }, 1L)
    }
}
