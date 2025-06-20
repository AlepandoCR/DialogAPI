package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.Input
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.BooleanInput

class BooleanInput(
    label: Component,
    private val initial: Boolean,
    private val onTrue: String,
    private val onFalse: String
    ) : Input<BooleanInput>(label), Wrapper<BooleanInput> {
    override fun toNMS(): BooleanInput {
        return BooleanInput(label,initial,onTrue,onFalse)
    }
}