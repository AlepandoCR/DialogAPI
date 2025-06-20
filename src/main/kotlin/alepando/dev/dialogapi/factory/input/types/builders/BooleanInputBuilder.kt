package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.types.BooleanInput
import alepando.dev.dialogapi.util.ComponentTranslator

typealias AdventureComponent = net.kyori.adventure.text.Component

class BooleanInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var initial: Boolean = false
    private var onTrue: String = "true"
    private var onFalse: String = "false"

    fun label(label: AdventureComponent) = apply { this.label = label }
    fun initial(value: Boolean) = apply { this.initial = value }
    fun onTrue(action: String) = apply { this.onTrue = action }
    fun onFalse(action: String) = apply { this.onFalse = action }

    fun build(): BooleanInput {
        return BooleanInput(ComponentTranslator.toNMS(label), initial, onTrue, onFalse)
    }
}
