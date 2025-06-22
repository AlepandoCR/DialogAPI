package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.options.MultilineOptions
import alepando.dev.dialogapi.factory.input.types.TextInput
import alepando.dev.dialogapi.util.Translator

/**
 * Builder class for creating [TextInput] instances.
 */
class TextInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var with: Int = 100
    private var labelVisible: Boolean = true
    private var initial: String = ""
    private var maxLength: Int = 255
    private var multiline: MultilineOptions? = null
    private var key: String = "not_defined"

    fun key(key: String) = apply { this.key = key }

    /** Sets the label for the text input. */
    fun label(label: AdventureComponent) = apply { this.label = label }

    /** Sets the width of the text input. */
    fun width(width: Int) = apply { this.with = width }

    /** Sets whether the label is visible. */
    fun labelVisible(visible: Boolean) = apply { this.labelVisible = visible }

    /** Sets the initial text content. */
    fun initial(text: String) = apply { this.initial = text }

    /** Sets the maximum character length. */
    fun maxLength(length: Int) = apply { this.maxLength = length }

    /** Sets the [MultilineOptions] for the text input. */
    fun multiline(multiline: MultilineOptions) = apply { this.multiline = multiline }

    /**
     * Builds the [TextInput] instance.
     * @return The created [TextInput].
     * @throws IllegalStateException if [multiline] options are not set.
     */
    fun build(): TextInput {
        multiline ?: throw IllegalStateException("Multiline options must be defined for TextInput.")
        return TextInput(Translator.componentToNMS(label), with, labelVisible,key, initial, maxLength, multiline!!)
    }
}
