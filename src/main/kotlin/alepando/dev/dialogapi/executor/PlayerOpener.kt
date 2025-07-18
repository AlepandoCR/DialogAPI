package alepando.dev.dialogapi.executor

import alepando.dev.dialogapi.executor.events.PlayerOpenDialogEvent
import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.versionSupplier.packet.ClientVersionSniffer
import alepando.dev.viaDialog.inventory.DialogInventory
import net.minecraft.core.Holder.Direct
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

/**
 * Provides extension functions for opening dialogs for players.
 */
object PlayerOpener{
    /**
     * Opens a dialog for the player.
     *
     * @param dialog The dialog to open.
     */
    fun Player.openDialog(dialog: Dialog) {
        val protocolVersion = ClientVersionSniffer.getProtocolVersionForUUID(uniqueId) ?: 0
        if (protocolVersion < 766) {
            DialogInventory().parse(this, dialog)
            return
        }

        val craftPlayer = player as CraftPlayer
        val nmsPlayer = craftPlayer.handle

        val holder = Direct(dialog.toNMS())

        PlayerOpenDialogEvent(this,dialog).callEvent()

        nmsPlayer.openDialog(holder)
    }
}


