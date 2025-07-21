package alepando.dev.viaDialog.listeners

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.packets.parser.PayloadParser.toCompoundTag
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.InputValue
import alepando.dev.dialogapi.util.InputValueList
import alepando.dev.dialogapi.util.Translator.toPlainText
import alepando.dev.viaDialog.inventory.DialogInventory
import net.kyori.adventure.text.Component
import net.minecraft.nbt.StringTag
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import java.util.*

open class AnvilListener(private val player: Player,private val dynamicListener: DynamicListener, private val list: MutableList<Input<*>>, private val button: Button, private val inventory: DialogInventory, private val input: Input<*>, private val dialog: Dialog) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.type != InventoryType.ANVIL) return
        if (event.whoClicked !is Player) return
        if (event.whoClicked != player) return
        if (event.rawSlot != 2) return

        event.isCancelled = true

        val inputItem = event.inventory.getItem(0)
        if (inputItem == null || !inputItem.hasItemMeta()) {
            DialogAPI.log("[InputProcessing] input item null or no meta")
            return
        }

        val renamedMeta = inputItem.itemMeta
        val renamedText = renamedMeta.displayName

        if (renamedText.isEmpty()) {
            DialogAPI.log("[InputProcessing] renamed text null/empty")
            return
        }

        val text = Component.text(renamedText)
        inventory.inputResponses[text] = button

        if (list.isEmpty()) {
            DialogAPI.log("[RemainingInputs] no inputs left")

            val resourceLocation = button.action
                .map { it.resourceLocation }
                .orElse(ResourceLocation("inventory_gui", "no_button"))


            val inputValues = InputValueList()

            inventory.inputResponses.forEach{
                val inputValue = InputValue(text,it.key.toPlainText())
                inputValues.add(inputValue)
            }

            val compoundTag = inputValues.toCompoundTag()

            val packet = ServerboundCustomClickActionPacket(
                resourceLocation.toNMS(),
                Optional.of(compoundTag)
            )

            PlayerDialogInteractionEvent(player, packet, DialogAPI.plugin!!).callEvent()
        }

        player.closeInventory()
        dynamicListener.stop()
        inventory.handleInputsSequentially(player, dialog, list, button)
    }

}
