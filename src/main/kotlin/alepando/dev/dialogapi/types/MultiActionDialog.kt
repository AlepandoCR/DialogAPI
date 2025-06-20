package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.server.dialog.ActionButton
import net.minecraft.server.dialog.MultiActionDialog
import java.util.*

/** Type alias for the NMS (Net Minecraft Server) `MultiActionDialog` class. */
typealias NMSMultiActionDialog = MultiActionDialog

/**
 * Represents a dialog that displays multiple action buttons.
 *
 * @property buttons A list of [Button] instances to be displayed.
 * @property exitButton An optional button to exit the dialog.
 * @property columns The number of columns to display the action buttons in.
 */
class MultiActionDialog(
    data: DialogData,
    private val buttons: List<Button>,
    private val exitButton: Optional<Button>,
    private val columns: Int

): Dialog(data) {
    /**
     * Converts this [MultiActionDialog] to its NMS equivalent.
     * @return The NMS [NMSMultiActionDialog].
     */
    override fun toNMS(): NMSMultiActionDialog {
        return NMSMultiActionDialog(data.toNMS(),toNMSButtonList(),toNMSOptionalButton(),columns)
    }

    /**
     * Converts the list of [Button] instances to a list of NMS [ActionButton] instances.
     * @return A list of NMS [ActionButton] instances.
     */
    private fun toNMSButtonList(): List<ActionButton>{
        val nmsList = mutableListOf<ActionButton>()

        for (button in buttons) {
            nmsList.add(button.toNMS())
        }

        return nmsList
    }

    /**
     * Converts the optional [Button] to its NMS [ActionButton] equivalent, wrapped in an [Optional].
     * @return An [Optional] containing the NMS [ActionButton] if [exitButton] is present, otherwise empty.
     */
    private fun toNMSOptionalButton(): Optional<ActionButton>{
        var nmsOptional = Optional.empty<ActionButton>()

        if (exitButton.isPresent) nmsOptional = Optional.of(exitButton.get().toNMS())

        return nmsOptional
    }
}