package alepando.dev.viaDialog.inventory

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.types.*
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.viaDialog.factory.InventoryFactory
import alepando.dev.viaDialog.guis.AnvilGUI
import alepando.dev.viaDialog.listeners.AnvilListener
import alepando.dev.viaDialog.listeners.InventoryListener
import org.bukkit.entity.Player
import java.util.*

class DialogInventory {

    private val plugin = DialogAPI.plugin!!
    private val inventoryButtons = mutableMapOf<UUID, List<Button>>()
    private val inputResponses = mutableMapOf<UUID, MutableMap<String, String>>()

    fun parse(player: Player, dialog: Dialog) {
        val inputs = dialog.data.inputs
        if (inputs.isNotEmpty()) {
            inputResponses[player.uniqueId] = mutableMapOf()
            handleInputsSequentially(player, dialog, inputs.iterator())
        } else {
            openInventoryForDialog(player, dialog)
        }
    }

    private fun handleInputsSequentially(player: Player, dialog: Dialog, iterator: Iterator<Input<*>>) {
        if (!iterator.hasNext()) {
            openInventoryForDialog(player, dialog)
            return
        }

        val input = iterator.next()
        val resourceLocation = getResourceLocationFromDialog(dialog, input.key)
        val inventory = AnvilGUI().create(player, input.toString())

        DynamicListener(plugin).apply {
            setListener(AnvilListener(resourceLocation, this))
            start()
        }

        player.openInventory(inventory)
    }

    private fun openInventoryForDialog(player: Player, dialog: Dialog) {
        val buttons = getButtonsFromDialog(dialog)
        val inventory = InventoryFactory().createInventory(dialog, buttons)

        inventoryButtons[player.uniqueId] = buttons
        player.openInventory(inventory)

        DynamicListener(plugin).apply {
            setListener(InventoryListener(buttons, this))
            start()
        }
    }

    private fun getButtonsFromDialog(dialog: Dialog): List<Button> {
        return when (dialog) {
            is MultiActionDialog -> getButtonsFromMultiActionDialog(dialog)
            is ConfirmationDialog -> getButtonsFromConfirmationDialog(dialog)
            is NoticeDialog -> listOf(dialog.button)
            else -> emptyList()
        }
    }

    private fun getButtonsFromMultiActionDialog(dialog: MultiActionDialog): List<Button> {
        return buildList {
            addAll(dialog.buttons)
            dialog.exitButton.ifPresent { add(it) }
        }
    }

    private fun getButtonsFromConfirmationDialog(dialog: ConfirmationDialog): List<Button> {
        return listOf(
            Button.fromNMS(dialog.yesButton),
            Button.fromNMS(dialog.noButton)
        )
    }

    private fun getResourceLocationFromDialog(dialog: Dialog, inputKey: String): ResourceLocation {
        val defaultNamespace = "inventory_gui"
        return when (dialog) {
            is MultiActionDialog -> dialog.buttons.firstOrNull()?.action?.get()?.resourceLocation
            is ConfirmationDialog -> dialog.yesButton.let { Button.fromNMS(it).action.get().resourceLocation }
            is NoticeDialog -> dialog.button.action.get().resourceLocation
            else -> null
        } ?: ResourceLocation(defaultNamespace, inputKey)
    }

    fun getInputResponses(player: Player): Map<String, String> {
        return inputResponses[player.uniqueId] ?: emptyMap()
    }

    fun clearInputResponses(player: Player) {
        inputResponses.remove(player.uniqueId)
    }
}
