package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.types.BooleanInput
import alepando.dev.dialogapi.util.ComponentTranslator

typealias AdventureComponent = net.kyori.adventure.text.Component

/**
 * Builder class for creating [BooleanInput] instances.
 */
class BooleanInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var initial: Boolean = false
    private var onTrue: String = "true"
    private var onFalse: String = "false"
    private var key: String = "not_defined"

    fun key(key: String) = apply { this.key = key }

    /** Sets the label for the boolean input. */
    fun label(label: AdventureComponent) = apply { this.label = label }

    /** Sets the initial value for the boolean input. */
    fun initial(value: Boolean) = apply { this.initial = value }

    /** Sets the text displayed when the value is true. */
    fun onTrue(action: String) = apply { this.onTrue = action }

    /** Sets the text displayed when the value is false. */
    fun onFalse(action: String) = apply { this.onFalse = action }

    /**
     * Builds the [BooleanInput] instance.
     * @return The created [BooleanInput].
     */
    fun build(): BooleanInput {
        return BooleanInput(ComponentTranslator.toNMS(label),key, initial, onTrue, onFalse)
    }
}
