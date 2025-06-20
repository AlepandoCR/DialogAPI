package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.server.dialog.ActionButton
import net.minecraft.server.dialog.ServerLinksDialog
import java.util.*

/** Type alias for the NMS (Net Minecraft Server) `ServerLinksDialog` class. */
typealias NMSServerLinksDialog = ServerLinksDialog

/**
 * Represents a dialog that displays a list of links as buttons.
 *
 * @property exitButton An optional button to exit the dialog.
 * @property columns The number of columns to display the link buttons in.
 * @property buttonWidth The width of each link button.
 */
class LinksDialog(
    data: DialogData,
    private val exitButton: Optional<Button>,
    private val columns: Int = 2,
    private val buttonWidth: Int = 150
) : Dialog(data) {

    /**
     * Converts this [LinksDialog] to its NMS equivalent.
     * @return The NMS [NMSServerLinksDialog].
     */
    override fun toNMS(): NMSServerLinksDialog {
        return NMSServerLinksDialog(
            data.toNMS(),
            toNMSOptionalButton(),
            columns,
            buttonWidth
        )
    }

    /**
     * Converts the optional [Button] to its NMS [ActionButton] equivalent, wrapped in an [Optional].
     * @return An [Optional] containing the NMS [ActionButton] if [exitButton] is present, otherwise empty.
     */
    private fun toNMSOptionalButton(): Optional<ActionButton> {
        return exitButton.map { it.toNMS() }
    }
}
