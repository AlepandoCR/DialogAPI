package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.nbt.StringTag
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import java.util.*

open class AnvilListener(private val dynamicListener: DynamicListener, private val iterator: MutableIterator<Input<*>>, private val buttons: List<Button>) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        var resourceLocation = ResourceLocation("inventory_gui","no_button")
        if(buttons.isNotEmpty()){
            val action = buttons.first().action
            if(action.isPresent){
                resourceLocation = action.get().resourceLocation
            }
        }
        if (event.inventory.type != InventoryType.ANVIL) return
        if (event.slot != 2) return
        val player = event.whoClicked

        if(player !is Player) return

        val item = event.currentItem ?: return
        val meta = item.itemMeta ?: return
        val text = meta.displayName

        val packet = ServerboundCustomClickActionPacket(resourceLocation.toNMS(), Optional.of(StringTag.valueOf(text)))

        PlayerDialogInteractionEvent(player,packet, DialogAPI.plugin!!).callEvent()

        iterator.remove()

        dynamicListener.stop()
    }
}
