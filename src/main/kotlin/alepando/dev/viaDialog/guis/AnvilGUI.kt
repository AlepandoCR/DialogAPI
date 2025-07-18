package alepando.dev.viaDialog.guis

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class AnvilGUI {

    fun create(player: Player, title: String): Inventory {
        val inventory = Bukkit.createInventory(player, org.bukkit.event.inventory.InventoryType.ANVIL, title)
        val paper = ItemStack(Material.PAPER)
        val meta = paper.itemMeta
        meta.setDisplayName("Input")
        paper.itemMeta = meta
        inventory.setItem(0, paper)
        return inventory
    }
}
