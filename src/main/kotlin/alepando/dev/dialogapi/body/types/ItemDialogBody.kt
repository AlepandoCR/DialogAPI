package alepando.dev.dialogapi.body.types

import alepando.dev.dialogapi.body.DialogBody
import net.minecraft.server.dialog.body.PlainMessage
import org.bukkit.craftbukkit.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import java.util.*

typealias NMSItemBody = net.minecraft.server.dialog.body.ItemBody

/**
 * Represents an item dialog body.
 *
 * @property item The item to display.
 * @property showDecorations Whether to show decorations.
 * @property showTooltip Whether to show the tooltip.
 * @property height The height of the dialog body.
 * @property description The description of the item.
 */
class ItemDialogBody(
    width: Int,
    private val item: ItemStack,
    private val showDecorations: Boolean,
    private val showTooltip: Boolean,
    private val height: Int,
    private val description: Optional<PlainMessage> = Optional.empty()
    ) : DialogBody<NMSItemBody>(width) {

    /**
     * Converts this dialog body to its NMS equivalent.
     * @return The NMS equivalent of this dialog body.
     */
    override fun toNMS(): NMSItemBody {
        return NMSItemBody(toNMSStack(),description,showDecorations,showTooltip,width,height)
    }

    /**
     * Converts the [ItemStack] to its NMS equivalent.
     * @return The NMS equivalent of the [ItemStack].
     */
    private fun toNMSStack(): net.minecraft.world.item.ItemStack{
        return  CraftItemStack.asCraftCopy(item).handle
    }
}