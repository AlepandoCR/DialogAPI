package alepando.dev.viaDialog.packet

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.executor.events.PlayerDialogInteractionEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.packets.PacketBuilder
import alepando.dev.dialogapi.util.DynamicListener
import alepando.dev.dialogapi.util.Translator.createNMSItem
import alepando.dev.dialogapi.util.Translator.toInputValueList
import alepando.dev.viaDialog.inventory.DialogInventory
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.inventory.AnvilMenu
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.MenuType
import org.bukkit.Material
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import net.minecraft.world.item.ItemStack as NMSItemStack

class AnvilInputPacketHandler(
    private val dynamicListener: DynamicListener,
    private val list: MutableList<Input<*>>,
    private val button: Button,
    private val dialog: Dialog,
    private val dialogInventory: DialogInventory,
    private val input: Input<*>,
    private val player: Player
) {

    fun openAnvil() {
        val nmsPlayer = (player as CraftPlayer).handle as ServerPlayer
        val containerId = nmsPlayer.nextContainerCounter()

        val title = Component.literal(input.label.string)

        // Crear el menu yunque con acceso al nivel
        val menu = AnvilMenu(
            containerId,
            nmsPlayer.inventory,
            ContainerLevelAccess.create(nmsPlayer.level(), nmsPlayer.blockPosition())
        )

        // Crear ítem a renombrar
        val item = createDefaultItem()

        // Obtener un stateId válido
        val stateId = nmsPlayer.id

        // Poner el ítem en el slot 0 (el que se puede renombrar)
        menu.setItem(0, stateId, item)

        // Asignar el nuevo contenedor al jugador
        nmsPlayer.containerMenu = menu

        // Enviar el paquete para abrir el menú
        nmsPlayer.connection.send(ClientboundOpenScreenPacket(containerId, MenuType.ANVIL, title))

        // Inicializar la sincronización de items
        nmsPlayer.initMenu(menu)

        // Inyectar listener
        injectRenamePacketListener(nmsPlayer)
    }

    private fun createDefaultItem(): NMSItemStack {
        val item = ItemStack(Material.PAPER)
        item.itemMeta?.customName(net.kyori.adventure.text.Component.text("Rename Me"))
        return item.createNMSItem()
    }


    private fun injectRenamePacketListener(nmsPlayer: ServerPlayer) {
        val channel = nmsPlayer.connection.connection.channel

        channel.pipeline().addBefore(
            "packet_handler",
            "anvil_input_listener_${player.uniqueId}",
            AnvilRenameHandler(player.uniqueId) { rename ->
                handleRename(rename)
            }
        )
    }

    private fun handleRename(rename: String) {
        if (rename.isBlank()) {
            DialogAPI.log("[AnvilPacketHandler] Rename is blank, ignoring")
            return
        }

        dialogInventory.inputResponses[input.key] = rename

        if (list.isEmpty()) {
            button.action.ifPresent { action ->
                val packet = PacketBuilder.build(action.resourceLocation, dialogInventory.inputResponses.toInputValueList())
                PlayerDialogInteractionEvent(player, packet).callEvent()
            }
        } else {
            dialogInventory.handleInputsSequentially(player, dialog, list, button)
        }

        dynamicListener.stop()
        player.closeInventory()
    }

}
