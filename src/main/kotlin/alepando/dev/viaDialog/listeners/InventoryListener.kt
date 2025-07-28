package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.viaDialog.inventory.DialogInventory
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class InventoryListener(private val buttons: List<Button>, private val dynamicListener: DynamicListener, private val dialog: Dialog, private val inventory: DialogInventory, private val buttonItems: MutableList<ItemStack>) : Listener {

    private val buttonMap = mutableMapOf<ItemStack, Button>()

    init {
        sortButtonMap()
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        DialogAPI.log("Handling click")
        val inputs = dialog.data.inputs
        val clickedItem = event.currentItem

        if(clickedItem == null){
            DialogAPI.log("ClickedItem null")
            return
        }

        val player = event.whoClicked

        if(player !is Player) {
            DialogAPI.log("Clicker is not player")
            return
        }

        val button = buttonMap[clickedItem]

        if(button == null){
            DialogAPI.log("Button not included")
            return
        }

        val action = button.action

        if(inputs.isNotEmpty()){
            DialogAPI.log("Handling Inputs")
            player.closeInventory()
            inventory.handleInputsSequentially(player,dialog,inputs,button)
        }else{
            if(action.isPresent){
                val actionAux = action.get()

                val packet = ServerboundCustomClickActionPacket(actionAux.resourceLocation.toNMS(),Optional.empty())
                player.closeInventory()

                PlayerDialogInteractionEvent(player,packet, DialogAPI.plugin!!).callEvent()
            }else{
                DialogAPI.log("No action present")
            }
        }
        dynamicListener.stop()
    }

    private fun sortButtonMap(){
        if(buttons.size != buttonItems.size) {
            DialogAPI.log("ItemButtons and Dialog Buttons size do not match")
            return
        }

        buttons.forEach {
            buttonMap[buttonItems[buttons.indexOf(it)]] = it
        }
    }
}
