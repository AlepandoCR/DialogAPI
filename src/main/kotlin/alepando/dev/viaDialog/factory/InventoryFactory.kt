package alepando.dev.viaDialog.factory

import alepando.dev.dialogapi.DialogAPI
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

    val buttonItems = mutableListOf<ItemStack>()

    fun createInventory(dialog: Dialog, buttons: List<Button>): Inventory {
        val inventory = Bukkit.createInventory(null, 9, dialog.data.title.toComponent())
        val body = dialog.data.dialogBody

        for (button in buttons) {
            val index = buttons.indexOf(button)
            val item = ItemStack(Material.PLAYER_HEAD, 1)
            buttonItems.add(index, item)
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
            itemMeta.customName(button.data.label.toComponent())
            item.itemMeta = itemMeta
            inventory.setItem(index, item)
            DialogAPI.log("[InvFactory] button added: $item")
        }

        return inventory
    }

}
