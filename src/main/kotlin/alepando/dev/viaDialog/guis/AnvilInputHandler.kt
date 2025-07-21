package alepando.dev.viaDialog.guis

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.Translator.toComponent
import alepando.dev.viaDialog.inventory.DialogInventory
import alepando.dev.viaDialog.listeners.AnvilInputListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.AnvilInventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class AnvilInputHandler {
    fun openAnvil(
        player: Player,
        list: MutableList<Input<*>>,
        button: Button,
        dialog: Dialog,
        dialogInventory: DialogInventory,
        input: Input<*>
    ) {
        val anvilInventory = Bukkit.createInventory(null, InventoryType.ANVIL, input.label.toComponent().color(NamedTextColor.DARK_GRAY))

        val paper = ItemStack(Material.PAPER)
        val meta: ItemMeta = paper.itemMeta
        val component = Component.text("Click to read me!")
        meta.customName(component)
        paper.itemMeta = meta

        anvilInventory.setItem(0, paper)

        DynamicListener().apply {
            setListener(AnvilInputListener(this, list, button, dialog, dialogInventory, input, player))
            start()
        }

        player.openInventory(anvilInventory)
    }
}
