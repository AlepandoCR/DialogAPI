package alepando.dev.viaDialog.guis

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.viaDialog.inventory.DialogInventory
import alepando.dev.viaDialog.listeners.SignInputListener
import io.papermc.paper.math.Position
import org.bukkit.block.sign.Side
import org.bukkit.entity.Player

class SignInputHandler {
    fun openSign(
        player: Player,
        list: MutableList<Input<*>>,
        button: Button,
        dialog: Dialog,
        dialogInventory: DialogInventory,
        input: Input<*>
    ) {
        val location = player.location
        val position = Position.block(location)

        DynamicListener().apply {
            setListener(SignInputListener(this, list, button, dialog, dialogInventory,input,player))
            start()
        }


        player.openVirtualSign(position, Side.FRONT)
    }
}
