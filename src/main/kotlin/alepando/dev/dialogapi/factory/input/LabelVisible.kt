package alepando.dev.dialogapi.factory.input

import net.minecraft.network.chat.Component

/**
 * Abstract base class for inputs where the label's visibility can be controlled.
 * Extends [SizeableInput].
 *
 * @param T The type of the NMS input control this input wraps.
 * @property labelVisible Whether the label is visible.
 */
abstract class LabelVisible<T>(label: Component, with: Int, val labelVisible: Boolean) : SizeableInput<T>(label, with) {
}