package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.util.Translator
import net.kyori.adventure.text.Component
import java.util.*

/**
 * Builder class for [ButtonData].
 */
class ButtonDataBuilder {
    private var label: Component = Component.text("N/D")
    private var width: Int = 100
    private var tooltip: Component = Component.empty()

    /**
     * Sets the label of the button.
     * @param label The label component.
     * @return This builder instance.
     */
    fun label(label: Component): ButtonDataBuilder {
        this.label = label
        return this
    }

    /**
     * Sets the width of the button.
     * @param width The width value.
     * @return This builder instance.
     */
    fun width(width: Int): ButtonDataBuilder {
        this.width = width
        return this
    }

    /**
     * Sets the tooltip of the button.
     * @param tooltip The tooltip component.
     * @return This builder instance.
     */
    fun tooltip(tooltip: Component): ButtonDataBuilder {
        this.tooltip = tooltip
        return this
    }

    /**
     * Builds the [ButtonData] instance.
     * @return The created [ButtonData].
     */
    fun build(): ButtonData {
        return ButtonData(Translator.componentToNMS(label), width, Optional.of(Translator.componentToNMS(tooltip)))
    }
}
