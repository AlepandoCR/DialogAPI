package alepando.dev.dialogapi.factory.input

import net.minecraft.network.chat.Component

abstract class SizeableInput<T>(label: Component, val with: Int) : Input<T>(label)