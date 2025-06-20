package alepando.dev.dialogapi.factory.input.options

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.server.dialog.input.NumberRangeInput
import java.util.*

typealias NMSRangeInfo = NumberRangeInput.RangeInfo

/**
 * Represents range information for a number range input.
 *
 * @property start The start of the range.
 * @property end The end of the range.
 * @property initial The initial value, if any.
 * @property step The step value, if any.
 */
class RangeInfo(
    private val start: Float,
    private val end: Float,
    private val initial: Optional<Float> = Optional.empty(),
    private val step: Optional<Float> = Optional.empty()
) : Wrapper<NMSRangeInfo> {
    /**
     * Converts this range info to its NMS equivalent.
     * @return The NMS [NMSRangeInfo].
     */
    override fun toNMS(): NMSRangeInfo {
        return NMSRangeInfo(start, end,initial,step)
    }
}