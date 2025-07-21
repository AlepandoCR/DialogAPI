package alepando.dev.viaDialog.guis

import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.Translator.toComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class AnvilGUI {

    fun create(player: Player, input: Input<*>): Inventory {
        val title = input.label.toComponent()
        val inventory = Bukkit.createInventory(player, InventoryType.ANVIL, title)
        val paper = ItemStack(Material.PAPER)
        val meta = paper.itemMeta
        meta.customName(Component.text("Input: ").append(title))
        paper.itemMeta = meta
        inventory.setItem(0, paper)
        return inventory
    }
}
