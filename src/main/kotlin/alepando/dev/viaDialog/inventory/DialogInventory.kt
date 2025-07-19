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

    var currentInput: Input<*>? = null
    private var buttons = mutableListOf<Button>()
    private val plugin = DialogAPI.plugin!!
    private val inventoryButtons = mutableMapOf<UUID, List<Button>>()
    private val inputResponses = mutableMapOf<UUID, MutableMap<String, String>>()

    fun parse(player: Player, dialog: Dialog) {
        val inputs = dialog.data.inputs
        openInventoryForDialog(player, dialog)
        if (inputs.isNotEmpty()) {
            inputResponses[player.uniqueId] = mutableMapOf()
            handleInputsSequentially(player, dialog, inputs.iterator(), buttons)
        }
    }

    private fun handleInputsSequentially(player: Player, dialog: Dialog, iterator: MutableIterator<Input<*>>, buttons: List<Button>) {
        if (!iterator.hasNext()) {
            openInventoryForDialog(player, dialog)
            return
        }

        val input = iterator.next()
        val action = buttons.firstOrNull()?.action ?: return
        if(action.isPresent){
            val resourceLocation = action.get().resourceLocation
            val inventory = AnvilGUI().create(player, input.label.toString())

            DynamicListener(plugin).apply {
                setListener(AnvilListener(this, iterator,buttons))
                start()
            }
            player.openInventory(inventory)
        }

    }

    private fun openInventoryForDialog(player: Player, dialog: Dialog) {
        val buttons = getButtonsFromDialog(dialog)
        this.buttons = buttons
        val inventory = InventoryFactory().createInventory(dialog, buttons)

        inventoryButtons[player.uniqueId] = buttons
        player.openInventory(inventory)

        DynamicListener(plugin).apply {
            setListener(InventoryListener(buttons, this))
            start()
        }
    }

    private fun getButtonsFromDialog(dialog: Dialog): MutableList<Button> {
        return when (dialog) {
            is MultiActionDialog -> getButtonsFromMultiActionDialog(dialog)
            is ConfirmationDialog -> getButtonsFromConfirmationDialog(dialog)
            is ListDialog -> mutableListOf(dialog.exitButton.get())
            is LinksDialog -> mutableListOf(dialog.exitButton.get())
            is NoticeDialog -> mutableListOf(dialog.button)
            else -> mutableListOf()
        }
    }

    private fun getButtonsFromMultiActionDialog(dialog: MultiActionDialog): MutableList<Button> {
        return buildList {
            addAll(dialog.buttons)
            dialog.exitButton.ifPresent { add(it) }
        }.toMutableList()
    }

    private fun getButtonsFromConfirmationDialog(dialog: ConfirmationDialog): MutableList<Button> {
        return mutableListOf(
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
