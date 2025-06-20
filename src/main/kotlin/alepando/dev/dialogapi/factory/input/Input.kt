package alepando.dev.dialogapi.factory.input

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component

abstract class Input<T>(
    val label: Component
) : Wrapper<T> {
}