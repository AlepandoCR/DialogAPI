package alepando.dev.dialogapi.factory.input

import net.minecraft.network.chat.Component

/**
 * Abstract base class for inputs that have a definable width.
 * Extends [Input].
 *
 * @param T The type of the NMS input control this input wraps.
 * @property with The width of the input element.
 */
abstract class SizeableInput<T>(label: Component, key: String, val with: Int) : Input<T>(label,key)