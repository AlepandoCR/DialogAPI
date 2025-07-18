package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class InventoryListener(private val buttons: List<Button>, private val dynamicListener: DynamicListener) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val clickedItem = event.currentItem ?: return
        val clickedSlot = event.slot

        val player = event.whoClicked

        if(player !is Player) return

        if (clickedSlot >= buttons.size) return

        val button = buttons[clickedSlot]

        val action = button.action

        if(action.isPresent){
            val actionAux = action.get()

            val packet = ServerboundCustomClickActionPacket(actionAux.resourceLocation.toNMS(),Optional.empty())

            PlayerDialogInteractionEvent(player,packet, DialogAPI.plugin!!).callEvent()
        }

        dynamicListener.stop()
    }
}
