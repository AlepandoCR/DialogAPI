package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.options.MultilineOptions
import alepando.dev.dialogapi.factory.input.types.TextInput
import alepando.dev.dialogapi.util.ComponentTranslator
import net.minecraft.network.chat.Component

class TextInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var with: Int = 100
    private var labelVisible: Boolean = true
    private var initial: String = ""
    private var maxLength: Int = 255
    private var multiline: MultilineOptions? = null

    fun label(label: AdventureComponent) = apply { this.label = label }
    fun width(width: Int) = apply { this.with = width }
    fun labelVisible(visible: Boolean) = apply { this.labelVisible = visible }
    fun initial(text: String) = apply { this.initial = text }
    fun maxLength(length: Int) = apply { this.maxLength = length }
    fun multiline(multiline: MultilineOptions) = apply { this.multiline = multiline }

    fun build(): TextInput {
        multiline ?: throw IllegalStateException("Multiline options must be defined for TextInput.")
        return TextInput(ComponentTranslator.toNMS(label), with, labelVisible, initial, maxLength, multiline!!)
    }
}
