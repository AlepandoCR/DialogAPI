package alepando.dev.dialogapi.factory.input

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component

/**
 * Abstract base class for all input types in a dialog.
 *
 * @param T The type of the NMS input control this input wraps.
 * @property label The label displayed for this input.
 */
abstract class Input<T>(
    val label: Component,
    val key: String
) : Wrapper<T>