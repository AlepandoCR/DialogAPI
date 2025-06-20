package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.LabelVisible
import alepando.dev.dialogapi.factory.input.options.MultilineOptions
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.TextInput
import java.util.*

class TextInput(
    label: Component,
    with: Int,
    labelVisible: Boolean,
    private val initial: String,
    private val maxLength: Int,
    private val multiline: MultilineOptions

) : LabelVisible<TextInput>(label, with, labelVisible), Wrapper<TextInput> {
    override fun toNMS(): TextInput {
        return TextInput(with,label,labelVisible,initial,maxLength,Optional.of(multiline.toNMS()))
    }
}