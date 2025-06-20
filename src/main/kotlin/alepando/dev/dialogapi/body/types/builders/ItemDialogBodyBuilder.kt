package alepando.dev.dialogapi.body.types.builders

import alepando.dev.dialogapi.body.types.ItemDialogBody
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.body.PlainMessage
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Builder class for [ItemDialogBody].
 */
class ItemDialogBodyBuilder {
    private var width: Int = 100
    private var item: ItemStack? = null
    private var description: Optional<PlainMessage> = Optional.empty()
    private var showDecorations: Boolean = true
    private var showTooltip: Boolean = true
    private var height: Int = 100

    /** Sets the width of the dialog body. */
    fun width(width: Int) = apply { this.width = width }

    /** Sets the item to display. */
    fun item(item: ItemStack) = apply { this.item = item }

    /** Sets the description of the item. */
    fun description(description: String,  width: Int) = apply { this.description = Optional.of(PlainMessage(Component.literal(description),width)) }

    /** Sets whether to show decorations. */
    fun showDecorations(show: Boolean) = apply { this.showDecorations = show }

    /** Sets whether to show the tooltip. */
    fun showTooltip(show: Boolean) = apply { this.showTooltip = show }

    /** Sets the height of the dialog body. */
    fun height(height: Int) = apply { this.height = height }

    /**
     * Builds the [ItemDialogBody].
     * @return The built [ItemDialogBody].
     * @throws IllegalArgumentException if the item is null.
     */
    fun build(): ItemDialogBody {
        requireNotNull(item) { "ItemStack must not be null" }
        return ItemDialogBody(width, item!!, showDecorations, showTooltip, height,description)
    }
}
