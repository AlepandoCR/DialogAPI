package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.nbt.StringTag
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import java.util.*

open class AnvilListener(private val resourceLocation: ResourceLocation, private val dynamicListener: DynamicListener) : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.type != InventoryType.ANVIL) return
        if (event.slot != 2) return
        val player = event.whoClicked

        if(player !is Player) return

        val item = event.currentItem ?: return
        val meta = item.itemMeta ?: return
        val text = meta.displayName

        val packet = ServerboundCustomClickActionPacket(resourceLocation.toNMS(), Optional.of(StringTag.valueOf(text)))

        PlayerDialogInteractionEvent(player,packet, DialogAPI.plugin!!).callEvent()

        dynamicListener.stop()
    }
}
