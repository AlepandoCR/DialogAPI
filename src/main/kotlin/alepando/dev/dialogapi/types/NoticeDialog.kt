package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.server.dialog.NoticeDialog

/** Type alias for the NMS (Net Minecraft Server) `NoticeDialog` class. */
typealias NMSNoticeDialog = NoticeDialog

/**
 * Represents a dialog that displays a notice or informational message with a single button.
 *
 * @property button The button to be displayed in the notice dialog.
 */
class NoticeDialog(
    data: DialogData,
    private val button: Button
) : Dialog(data) {

    /**
     * Converts this [NoticeDialog] to its NMS equivalent.
     * @return The NMS [NMSNoticeDialog].
     */
    override fun toNMS(): NMSNoticeDialog {
        return NMSNoticeDialog(
            data.toNMS(),
            button.toNMS()
        )
    }
}
