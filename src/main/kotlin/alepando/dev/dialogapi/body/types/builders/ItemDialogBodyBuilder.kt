package alepando.dev.dialogapi.body.types.builders

import alepando.dev.dialogapi.body.types.ItemDialogBody
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.body.PlainMessage
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemDialogBodyBuilder {
    private var width: Int = 100
    private var item: ItemStack? = null
    private var description: Optional<PlainMessage> = Optional.empty()
    private var showDecorations: Boolean = true
    private var showTooltip: Boolean = true
    private var height: Int = 100

    fun width(width: Int) = apply { this.width = width }
    fun item(item: ItemStack) = apply { this.item = item }
    fun description(description: String,  width: Int) = apply { this.description = Optional.of(PlainMessage(Component.literal(description),width)) }
    fun showDecorations(show: Boolean) = apply { this.showDecorations = show }
    fun showTooltip(show: Boolean) = apply { this.showTooltip = show }
    fun height(height: Int) = apply { this.height = height }

    fun build(): ItemDialogBody {
        requireNotNull(item) { "ItemStack must not be null" }
        return ItemDialogBody(width, item!!, showDecorations, showTooltip, height,description)
    }
}
