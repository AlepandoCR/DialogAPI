package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.MultiActionDialog
import java.lang.IllegalStateException
import java.util.*

/**
 * Builder class for creating [MultiActionDialog] instances.
 */
class MultiActionDialogBuilder {
    private lateinit var data: DialogData
    private var buttons: MutableList<Button> = mutableListOf()
    private var exitButton: Optional<Button> = Optional.empty()
    private var columns: Int = 1

    /** Sets the [DialogData] for the multi-action dialog. */
    fun data(data: DialogData) = apply { this.data = data }

    /** Sets the list of action buttons for the dialog. */
    fun buttons(buttons: List<Button>) = apply { this.buttons = buttons.toMutableList() }

    /** Adds an action button to the dialog. */
    fun addButton(button: Button) = apply { this.buttons.add(button) }

    /** Sets the optional exit button for the dialog. */
    fun exitButton(exitButton: Button?) = apply { this.exitButton = Optional.ofNullable(exitButton) }

    /** Sets the number of columns for displaying action buttons. */
    fun columns(columns: Int) = apply { this.columns = columns }

    /**
     * Builds the [MultiActionDialog] instance.
     * @return The created [MultiActionDialog].
     * @throws UninitializedPropertyAccessException if [data] is not set.
     */
    fun build(): MultiActionDialog {
        if(buttons.isEmpty()) throw IllegalStateException("MultiActionDialogs should have at least 1 Button")
        return MultiActionDialog(data, buttons, exitButton, columns)
    }
}
