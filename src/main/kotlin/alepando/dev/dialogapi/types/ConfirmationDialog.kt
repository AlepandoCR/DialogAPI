package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.server.dialog.ActionButton
import java.util.*

/**
 * Type alias for the NMS (Net Minecraft Server) `ConfirmationDialog` class.
 * This is the server's underlying representation of a confirmation dialog.
 */
typealias NMSConfirmationDialog = net.minecraft.server.dialog.ConfirmationDialog

/**
 * Represents a dialog that presents the user with a binary choice, typically "Yes" and "No".
 * This dialog type is used for confirming actions or decisions.
 *
 * @param yesButton The NMS [ActionButton] representing the "Yes" choice.
 * @param noButton The NMS [ActionButton] representing the "No" choice.
 * @param data The [DialogData] containing the core information and configuration for this dialog.
 * @param dynamicListener An optional [DynamicListener] for handling Bukkit events related to this dialog.
 */
class ConfirmationDialog(
    private val yesButton: ActionButton,
    private val noButton: ActionButton, data: DialogData,
    dynamicListener: Optional<DynamicListener> = Optional.empty()
): Dialog(data, dynamicListener){

    /**
     * Converts this [ConfirmationDialog] instance into its corresponding NMS (Net Minecraft Server)
     * [NMSConfirmationDialog] representation.
     * This is used internally to interact with the server's dialog system.
     *
     * @return The NMS [NMSConfirmationDialog] object.
     */
    override fun toNMS(): NMSConfirmationDialog {
        return NMSConfirmationDialog(data.toNMS(),yesButton,noButton)
    }

}