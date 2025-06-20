package alepando.dev.dialogapi.factory.input.options

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.server.dialog.input.TextInput
import java.util.*

class MultilineOptions(
    private val maxLines: Int,
    private val height: Int
): Wrapper<TextInput.MultilineOptions> {
    override fun toNMS(): TextInput.MultilineOptions {
        return TextInput.MultilineOptions(Optional.of(maxLines),Optional.of(height))
    }
}