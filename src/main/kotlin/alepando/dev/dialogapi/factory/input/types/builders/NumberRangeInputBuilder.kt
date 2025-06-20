package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.options.RangeInfo
import alepando.dev.dialogapi.factory.input.types.NumberRangeInput
import alepando.dev.dialogapi.util.ComponentTranslator

/**
 * Builder class for creating [NumberRangeInput] instances.
 */
class NumberRangeInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var with: Int = 100
    private var labelFormat: String = ""
    private var rangeInfo: RangeInfo? = null

    /** Sets the label for the number range input. */
    fun label(label: AdventureComponent) = apply { this.label = label }

    /** Sets the width of the number range input. */
    fun width(width: Int) = apply { this.with = width }

    /** Sets the label format string for the number range input. */
    fun labelFormat(format: String) = apply { this.labelFormat = format }

    /** Sets the [RangeInfo] for the number range input. */
    fun rangeInfo(rangeInfo: RangeInfo) = apply { this.rangeInfo = rangeInfo }

    /**
     * Builds the [NumberRangeInput] instance.
     * @return The created [NumberRangeInput].
     * @throws IllegalStateException if [rangeInfo] is not set.
     */
    fun build(): NumberRangeInput {

        rangeInfo ?: throw IllegalStateException("RangeInfo must be set for NumberRangeInput.")

        return NumberRangeInput(ComponentTranslator.toNMS(label), with, labelFormat, rangeInfo!!)
    }
}
