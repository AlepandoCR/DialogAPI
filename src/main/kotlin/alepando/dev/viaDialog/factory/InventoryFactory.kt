package alepando.dev.viaDialog.factory

import alepando.dev.dialogapi.body.types.PlainMessageDialogBody
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.util.Translator.toComponent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class InventoryFactory {

    fun createInventory(dialog: Dialog, buttons: List<Button>): Inventory {
        val inventory = Bukkit.createInventory(null, 9, dialog.data.title.toComponent())
        val body = dialog.data.dialogBody

        for ((index, button) in buttons.withIndex()) {
            val item = ItemStack(Material.PLAYER_HEAD, 1)
            val itemMeta = item.itemMeta
            var description: Component? = null
            val bodyPart = body[index]
            if(bodyPart is PlainMessageDialogBody){
                description = bodyPart.contents
            }
            description?.let {
                val lore = listOf(description)
                itemMeta.lore(lore)
            }
            itemMeta.displayName(button.data.label.toComponent())
            item.itemMeta = itemMeta
            inventory.setItem(index, item)
        }

        return inventory
    }

}
