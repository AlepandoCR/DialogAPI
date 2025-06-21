package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.SizeableInput
import alepando.dev.dialogapi.factory.input.options.RangeInfo
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.NumberRangeInput

/**
 * Represents a number range input field in a dialog.
 *
 * @property labelFormat The format string for the label.
 * @property rangeInfo The [RangeInfo] defining the range.
 */
class NumberRangeInput(
    label: Component,
    with: Int,
    key: String,
    private val labelFormat: String,
    private val rangeInfo: RangeInfo
) : SizeableInput<NumberRangeInput>(label,key, with), Wrapper<NumberRangeInput> {
    /**
     * Converts this number range input to its NMS equivalent.
     * @return The NMS [NumberRangeInput].
     */
    override fun toNMS(): NumberRangeInput {
        return NumberRangeInput(with,label,labelFormat,rangeInfo.toNMS())
    }
}