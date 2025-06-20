package alepando.dev.dialogapi.factory.input.options.builders

import alepando.dev.dialogapi.factory.input.options.RangeInfo
import java.util.*

class RangeInfoBuilder {
    private var start: Float = 1f
    private var end: Float = 1f
    private var initial: Optional<Float> = Optional.empty()
    private var step: Optional<Float> = Optional.empty()

    fun start(start: Float) = apply { this.start = start }
    fun end(end: Float) = apply { this.end = end }
    fun initial(initial: Float?) = apply { this.initial = Optional.ofNullable(initial) }
    fun step(step: Float?) = apply { this.step = Optional.ofNullable(step) }

    fun build(): RangeInfo {
        return RangeInfo(start, end, initial, step)
    }
}
