package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.LabelVisible
import alepando.dev.dialogapi.factory.input.options.MultilineOptions
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.TextInput
import java.util.*

/**
 * Represents a text input field in a dialog.
 *
 * @property initial The initial text content of the input field.
 * @property maxLength The maximum number of characters allowed in the input field.
 * @property multiline The [MultilineOptions] for this text input, defining behavior for multiline input.
 */
class TextInput(
    label: Component,
    with: Int,
    labelVisible: Boolean,
    key: String,
    private val initial: String,
    private val maxLength: Int,
    private val multiline: MultilineOptions

) : LabelVisible<TextInput>(label,key, with, labelVisible), Wrapper<TextInput> {
    /**
     * Converts this text input to its NMS equivalent.
     * @return The NMS [TextInput].
     */
    override fun toNMS(): TextInput {
        return TextInput(with,label,labelVisible,initial,maxLength,Optional.of(multiline.toNMS()))
    }
}