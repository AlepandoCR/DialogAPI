package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.util.ComponentTranslator
import net.kyori.adventure.text.Component
import java.util.*

class ButtonDataBuilder {
    private var label: Component = Component.text("N/D")
    private var width: Int = 100
    private var tooltip: Component = Component.empty()

    fun label(label: Component): ButtonDataBuilder {
        this.label = label
        return this
    }

    fun width(width: Int): ButtonDataBuilder {
        this.width = width
        return this
    }

    fun tooltip(tooltip: Component): ButtonDataBuilder {
        this.tooltip = tooltip
        return this
    }

    fun build(): ButtonData {
        return ButtonData(ComponentTranslator.toNMS(label), width, Optional.of(ComponentTranslator.toNMS(tooltip)))
    }
}
