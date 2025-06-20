package alepando.dev.dialogapi.factory.input.options.builders

import alepando.dev.dialogapi.factory.input.options.RangeInfo
import java.util.*

/**
 * Builder class for creating [RangeInfo] instances.
 */
class RangeInfoBuilder {
    private var start: Float = 1f
    private var end: Float = 1f
    private var initial: Optional<Float> = Optional.empty()
    private var step: Optional<Float> = Optional.empty()

    /** Sets the start of the range. */
    fun start(start: Float) = apply { this.start = start }

    /** Sets the end of the range. */
    fun end(end: Float) = apply { this.end = end }

    /** Sets the initial value of the range. */
    fun initial(initial: Float?) = apply { this.initial = Optional.ofNullable(initial) }

    /** Sets the step value of the range. */
    fun step(step: Float?) = apply { this.step = Optional.ofNullable(step) }

    /**
     * Builds the [RangeInfo] instance.
     * @return The created [RangeInfo].
     */
    fun build(): RangeInfo {
        return RangeInfo(start, end, initial, step)
    }
}
