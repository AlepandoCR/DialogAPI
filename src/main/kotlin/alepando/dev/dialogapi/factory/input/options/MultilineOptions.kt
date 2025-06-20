package alepando.dev.dialogapi.factory.input.options

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.server.dialog.input.TextInput
import java.util.*

/**
 * Represents options for a multiline text input.
 *
 * @property maxLines The maximum number of lines allowed.
 * @property height The height of the input field.
 */
class MultilineOptions(
    private val maxLines: Int,
    private val height: Int
): Wrapper<TextInput.MultilineOptions> {
    /**
     * Converts these multiline options to their NMS equivalent.
     * @return The NMS [TextInput.MultilineOptions].
     */
    override fun toNMS(): TextInput.MultilineOptions {
        return TextInput.MultilineOptions(Optional.of(maxLines),Optional.of(height))
    }
}