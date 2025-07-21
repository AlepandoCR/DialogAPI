package alepando.dev.viaDialog.guis

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.Translator.toComponent
import alepando.dev.viaDialog.inventory.DialogInventory
import alepando.dev.viaDialog.listeners.BookInputListener
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class BookInputHandler {
    fun giveBook(
        player: Player,
        input: Input<*>,
        list: MutableList<Input<*>>,
        button: Button,
        dialog: Dialog,
        inventory: DialogInventory,
        plugin: Plugin
    ) {
        val book = ItemStack(Material.WRITABLE_BOOK)
        val meta = book.itemMeta
        meta.customName(input.label.toComponent())
        book.itemMeta = meta
        player.openBook(book)

        val listener = DynamicListener(plugin).apply {
            setListener(BookInputListener(player, this, list, button, inventory, input, dialog))
        }
        listener.start()
    }
}
