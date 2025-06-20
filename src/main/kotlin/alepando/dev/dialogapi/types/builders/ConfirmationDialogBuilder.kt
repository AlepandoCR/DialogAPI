package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.ConfirmationDialog
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.server.dialog.ActionButton
import java.util.*

/**
 * A fluent builder for creating [ConfirmationDialog] instances.
 * This builder helps in configuring the properties of a confirmation dialog,
 * such as the "yes" and "no" buttons, dialog data, and an optional dynamic listener.
 */
class ConfirmationDialogBuilder {
    private var yesButton: ActionButton? = null
    private var noButton: ActionButton? = null
    private var data: DialogData? = null
    private var dynamicListener: Optional<DynamicListener> = Optional.empty()

    /**
     * Sets the "Yes" button for the confirmation dialog.
     * The provided [Button] is converted to its NMS representation.
     * @param button The [Button] to be used as the "Yes" button.
     * @return This [ConfirmationDialogBuilder] instance for chaining.
     */
    fun yesButton(button: Button): ConfirmationDialogBuilder {
        yesButton = button.toNMS()
        return this
    }

    /**
     * Sets the "No" button for the confirmation dialog.
     * The provided [Button] is converted to its NMS representation.
     * @param button The [Button] to be used as the "No" button.
     * @return This [ConfirmationDialogBuilder] instance for chaining.
     */
    fun noButton(button: Button): ConfirmationDialogBuilder {
        noButton = button.toNMS()
        return this
    }

    /**
     * Sets the core [DialogData] for the confirmation dialog.
     * This data includes title, body, inputs, and other common dialog properties.
     * @param dialogData The [DialogData] to be used.
     * @return This [ConfirmationDialogBuilder] instance for chaining.
     */
    fun data(dialogData: DialogData): ConfirmationDialogBuilder {
        this.data = dialogData
        return this
    }

    /**
     * Sets an optional [DynamicListener] for the confirmation dialog.
     * This listener can be used to handle specific Bukkit events.
     * @param listener The [DynamicListener] to be associated with the dialog.
     * @return This [ConfirmationDialogBuilder] instance for chaining.
     */
    fun dynamicListener(listener: DynamicListener): ConfirmationDialogBuilder {
        this.dynamicListener = Optional.of(listener)
        return this
    }

    /**
     * Constructs a [ConfirmationDialog] instance with the properties configured in this builder.
     * Requires "Yes" button, "No" button, and [DialogData] to be set beforehand.
     * @return A new [ConfirmationDialog] instance.
     * @throws IllegalStateException if any of the required properties (yesButton, noButton, data) are not set.
     */
    fun build(): ConfirmationDialog {
        val yes = yesButton ?: throw IllegalStateException("Yes button must be set")
        val no = noButton ?: throw IllegalStateException("No button must be set")
        val dialogData = data ?: throw IllegalStateException("DialogData must be set")

        return ConfirmationDialog(yes, no, dialogData, dynamicListener)
    }
}
