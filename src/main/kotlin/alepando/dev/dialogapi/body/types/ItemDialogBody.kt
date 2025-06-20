package alepando.dev.dialogapi.body.types

import alepando.dev.dialogapi.body.DialogBody
import net.minecraft.server.dialog.body.PlainMessage
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import java.util.*

typealias NMSItemBody = net.minecraft.server.dialog.body.ItemBody

class ItemDialogBody(
    width: Int,
    private val item: ItemStack,
    private val showDecorations: Boolean,
    private val showTooltip: Boolean,
    private val height: Int,
    private val description: Optional<PlainMessage> = Optional.empty()
    ) : DialogBody<NMSItemBody>(width) {
    override fun toNMS(): NMSItemBody {
        return NMSItemBody(toNMSStack(),description,showDecorations,showTooltip,width,height)
    }

    private fun toNMSStack(): net.minecraft.world.item.ItemStack{
        return  CraftItemStack.asCraftCopy(item).handle
    }
}