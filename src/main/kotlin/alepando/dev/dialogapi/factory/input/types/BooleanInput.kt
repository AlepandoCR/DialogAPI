package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.Input
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.BooleanInput

/**
 * Represents a boolean input field in a dialog.
 *
 * @property initial The initial value of the boolean input.
 * @property onTrue The text displayed when the value is true.
 * @property onFalse The text displayed when the value is false.
 */
class BooleanInput(
    label: Component,
    key: String,
    private val initial: Boolean,
    private val onTrue: String,
    private val onFalse: String
    ) : Input<BooleanInput>(label,key), Wrapper<BooleanInput> {
    /**
     * Converts this boolean input to its NMS equivalent.
     * @return The NMS [BooleanInput].
     */
    override fun toNMS(): BooleanInput {
        return BooleanInput(label,initial,onTrue,onFalse)
    }
}