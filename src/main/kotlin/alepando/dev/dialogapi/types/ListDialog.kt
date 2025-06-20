package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.server.dialog.ActionButton
import net.minecraft.server.dialog.DialogListDialog
import java.util.*

/** Type alias for the NMS (Net Minecraft Server) `DialogListDialog` class. */
typealias NMSDialogListDialog = DialogListDialog

/**
 * Represents a dialog that displays a list of other dialogs as selectable buttons.
 *
 * @property dialogs A list of NMS [net.minecraft.server.dialog.Dialog] instances to be displayed.
 * @property exitButton An optional button to exit the dialog.
 * @property columns The number of columns to display the dialog buttons in.
 * @property buttonWidth The width of each dialog button.
 */
class ListDialog(
    data: DialogData,
    private val dialogs: List<net.minecraft.server.dialog.Dialog>,
    private val exitButton: Optional<Button>,
    private val columns: Int = 2,
    private val buttonWidth: Int = 150
) : Dialog(data){

    /**
     * Converts this [ListDialog] to its NMS equivalent.
     * @return The NMS [NMSDialogListDialog].
     */
    override fun toNMS(): NMSDialogListDialog {
        return NMSDialogListDialog(
            data.toNMS(),
            toNMSDialogHolderSet(),
            toNMSOptionalButton(),
            columns,
            buttonWidth
        )
    }

    /**
     * Converts the list of NMS [net.minecraft.server.dialog.Dialog] instances to a [HolderSet] of dialogs.
     * @return A [HolderSet] containing the NMS dialogs.
     */
    private fun toNMSDialogHolderSet(): HolderSet<net.minecraft.server.dialog.Dialog> {
        val holders = dialogs.map { Holder.direct(it) }
        return HolderSet.direct(holders)
    }

    /**
     * Converts the optional [Button] to its NMS [ActionButton] equivalent, wrapped in an [Optional].
     * @return An [Optional] containing the NMS [ActionButton] if [exitButton] is present, otherwise empty.
     */
    private fun toNMSOptionalButton(): Optional<ActionButton> {
        return exitButton.map { it.toNMS() }
    }
}
