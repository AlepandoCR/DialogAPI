package alepando.dev.viaDialog.inventory

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.types.*
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.viaDialog.factory.InventoryFactory
import alepando.dev.viaDialog.listeners.InventoryListener
import alepando.dev.viaDialog.packet.AnvilInputPacketHandler
import org.bukkit.entity.Player
import java.util.*

class DialogInventory {

    private var buttons = mutableListOf<Button>()
    private val plugin = DialogAPI.plugin!!
    private val inventoryButtons = mutableMapOf<UUID, List<Button>>()
    val inputResponses = mutableMapOf<String, String>()

    fun parse(player: Player, dialog: Dialog) {
        openInventoryForDialog(player, dialog)
    }

    fun handleInputsSequentially(player: Player, dialog: Dialog, list: MutableList<Input<*>>, button: Button) {
        DialogAPI.log("[handleInputsSequentially] invoked")
        if (list.isEmpty()) {
            DialogAPI.log("Input list empty")
            return
        }

        DialogAPI.log("[RemainingInputs] ${list.size}")
        list.forEach {
            DialogAPI.log("[RemainingInputs] ${it.label.string}")
        }

        val input = list.firstOrNull() ?: return
        list.remove(input)

        val action = button.action
        if (action.isPresent) {
            DialogAPI.log("[handleInputsSequentially] opening input: ${input.key}")
            openInputMenu(player, input, list, button, dialog)
        } else {
            DialogAPI.log("[handleInputsSequentially] no action present")
        }
    }

    private fun openInputMenu(
        player: Player,
        input: Input<*>,
        list: MutableList<Input<*>>,
        button: Button,
        dialog: Dialog
    ) {
        val dynamicListener = DynamicListener(DialogAPI.plugin!!)
        dynamicListener.start()

        AnvilInputPacketHandler(
            dynamicListener,
            list,
            button,
            dialog,
            this,
            input,
            player
        ).openAnvil()
    }

    private fun openInventoryForDialog(player: Player, dialog: Dialog) {
        this.buttons = getButtonsFromDialog(dialog)
        buttons.forEach {
            DialogAPI.log("[Button] button: ${it.data.label.string}")
        }
        val factory = InventoryFactory()
        val inventory = factory.createInventory(dialog, buttons)
        val itemButtons = factory.buttonItems

        val listener = DynamicListener(plugin).apply {
            setListener(InventoryListener(buttons, this, dialog, this@DialogInventory, itemButtons))
        }
        listener.start()
        DialogAPI.log("[Listener] InventoryListener started")
        player.openInventory(inventory)
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
        val exitButton = dialog.exitButton
        return buildList {
            addAll(dialog.buttons)
            if (exitButton.isPresent) {
                add(exitButton.get())
            }
        }.toMutableList()
    }

    private fun getButtonsFromConfirmationDialog(dialog: ConfirmationDialog): MutableList<Button> {
        return mutableListOf(
            Button.fromNMS(dialog.yesButton),
            Button.fromNMS(dialog.noButton)
        )
    }

    fun clearInputResponses() {
        inputResponses.clear()
    }
}
