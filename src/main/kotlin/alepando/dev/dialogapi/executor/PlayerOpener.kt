package alepando.dev.dialogapi.executor

import alepando.dev.dialogapi.factory.Dialog
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
        val craftPlayer = player as CraftPlayer
        val nmsPlayer = craftPlayer.handle

        val holder = Direct(dialog.toNMS())

        nmsPlayer.openDialog(holder)
    }
}


