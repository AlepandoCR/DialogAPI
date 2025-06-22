package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.ListDialog
import java.util.*

/**
 * Builder class for creating [ListDialog] instances.
 */
class ListDialogBuilder {
    private lateinit var data: DialogData
    private var dialogs: MutableList<Dialog> = mutableListOf()
    private var exitButton: Optional<Button> = Optional.empty()
    private var columns: Int = 2
    private var buttonWidth: Int = 150

    /** Sets the [DialogData] for the list dialog. */
    fun data(data: DialogData) = apply { this.data = data }

    /** Sets the list of NMS dialogs to be displayed. */
    fun dialogs(dialogs: List<Dialog>) = apply { this.dialogs = dialogs.toMutableList() }

    /** Adds an NMS dialog to the list of dialogs to be displayed. */
    fun addDialog(dialog: Dialog) = apply { this.dialogs.add(dialog) }

    /** Sets the optional exit button for the list dialog. */
    fun exitButton(exitButton: Button?) = apply { this.exitButton = Optional.ofNullable(exitButton) }

    /** Sets the number of columns for displaying dialog buttons. */
    fun columns(columns: Int) = apply { this.columns = columns }

    /** Sets the width for each dialog button. */
    fun buttonWidth(buttonWidth: Int) = apply { this.buttonWidth = buttonWidth }

    /**
     * Builds the [ListDialog] instance.
     * @return The created [ListDialog].
     * @throws UninitializedPropertyAccessException if [data] is not set.
     */
    fun build(): ListDialog {
        return ListDialog(data, dialogs, exitButton, columns, buttonWidth)
    }
}
