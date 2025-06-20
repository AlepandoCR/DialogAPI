package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.LinksDialog
import java.util.*

/**
 * Builder class for creating [LinksDialog] instances.
 */
class LinksDialogBuilder {
    private lateinit var data: DialogData
    private var exitButton: Optional<Button> = Optional.empty()
    private var columns: Int = 2
    private var buttonWidth: Int = 150

    /** Sets the [DialogData] for the links dialog. */
    fun data(data: DialogData) = apply { this.data = data }

    /** Sets the optional exit button for the links dialog. */
    fun exitButton(exitButton: Button?) = apply { this.exitButton = Optional.ofNullable(exitButton) }

    /** Sets the number of columns for displaying link buttons. */
    fun columns(columns: Int) = apply { this.columns = columns }

    /** Sets the width for each link button. */
    fun buttonWidth(buttonWidth: Int) = apply { this.buttonWidth = buttonWidth }

    /**
     * Builds the [LinksDialog] instance.
     * @return The created [LinksDialog].
     * @throws UninitializedPropertyAccessException if [data] is not set.
     */
    fun build(): LinksDialog {
        return LinksDialog(data, exitButton, columns, buttonWidth)
    }
}
