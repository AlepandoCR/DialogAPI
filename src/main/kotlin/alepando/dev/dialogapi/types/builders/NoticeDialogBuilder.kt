package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.NoticeDialog

/**
 * Builder class for creating [NoticeDialog] instances.
 */
class NoticeDialogBuilder {
    private lateinit var data: DialogData
    private lateinit var button: Button

    /** Sets the [DialogData] for the notice dialog. */
    fun data(data: DialogData) = apply { this.data = data }

    /** Sets the button for the notice dialog. */
    fun button(button: Button) = apply { this.button = button }

    /**
     * Builds the [NoticeDialog] instance.
     * @return The created [NoticeDialog].
     * @throws UninitializedPropertyAccessException if [data] or [button] is not set.
     */
    fun build(): NoticeDialog {
        return NoticeDialog(data, button)
    }
}
