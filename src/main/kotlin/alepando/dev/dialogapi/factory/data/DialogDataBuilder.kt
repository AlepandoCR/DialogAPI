package alepando.dev.dialogapi.factory.data

import alepando.dev.dialogapi.body.DialogBody
import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.ComponentTranslator
import net.kyori.adventure.text.Component
import net.minecraft.server.dialog.DialogAction
import java.util.*

/**
 * A fluent builder for creating [DialogData] instances.
 * Provides methods to set various properties of a dialog.
 */
class DialogDataBuilder {
    private var title: Component = Component.text("N/D")
    private var externalTitle: Component = Component.empty()
    private var canCloseWithEscape: Boolean = true
    private var pause: Boolean = false
    private var afterAction: DialogAction = DialogAction.CLOSE
    private var dialogBody: MutableList<DialogBody<*>> = mutableListOf()
    private var inputs: MutableList<Input<*>> = mutableListOf()

    /**
     * Sets the main title for the dialog.
     * @param title The [Component] to be used as the title.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun title(title: Component): DialogDataBuilder {
        this.title = title
        return this
    }

    /**
     * Sets an optional external title for the dialog.
     * This title might be displayed in the UI frame surrounding the dialog.
     * @param externalTitle The [Component] to be used as the external title. Can be null for no external title.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun externalTitle(externalTitle: Component): DialogDataBuilder {
        this.externalTitle = externalTitle
        return this
    }

    /**
     * Sets whether the dialog can be closed using the Escape key.
     * @param canClose True if Escape should close the dialog, false otherwise.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun canCloseWithEscape(canClose: Boolean): DialogDataBuilder {
        this.canCloseWithEscape = canClose
        return this
    }

    /**
     * Sets whether the game world should be paused while the dialog is open.
     * @param pause True to pause the game, false otherwise.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun pause(pause: Boolean): DialogDataBuilder {
        this.pause = pause
        return this
    }

    /**
     * Sets the default action to be performed after the dialog is closed or an action is triggered.
     * @param action The [DialogAction] to perform.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun afterAction(action: DialogAction): DialogDataBuilder {
        this.afterAction = action
        return this
    }

    /**
     * Adds a [DialogBody] element to the content of the dialog.
     * Elements will be displayed in the order they are added.
     * @param body The [DialogBody] element to add.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun addBody(body: DialogBody<*>): DialogDataBuilder {
        this.dialogBody.add(body)
        return this
    }

    /**
     * Sets the list of [DialogBody] elements for the dialog, replacing any previously added bodies.
     * @param bodies The list of [DialogBody] elements.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun setBody(bodies: List<DialogBody<*>>): DialogDataBuilder {
        this.dialogBody = bodies.toMutableList()
        return this
    }

    /**
     * Adds an [Input] element to the dialog, allowing for user interaction.
     * Inputs will be displayed in the order they are added.
     * @param input The [Input] element to add.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun addInput(input: Input<*>): DialogDataBuilder {
        this.inputs.add(input)
        return this
    }

    /**
     * Sets the list of [Input] elements for the dialog, replacing any previously added inputs.
     * @param inputs The list of [Input] elements.
     * @return This [DialogDataBuilder] instance for chaining.
     */
    fun setInputs(inputs: List<Input<*>>): DialogDataBuilder {
        this.inputs = inputs.toMutableList()
        return this
    }

    /**
     * Constructs a [DialogData] instance with the properties configured in this builder.
     * @return A new [DialogData] instance.
     */
    fun build(): DialogData {
        return DialogData(
            ComponentTranslator.toNMS(title),
            Optional.of(ComponentTranslator.toNMS(externalTitle)),
            canCloseWithEscape,
            pause,
            afterAction,
            dialogBody,
            inputs
        )
    }
}
