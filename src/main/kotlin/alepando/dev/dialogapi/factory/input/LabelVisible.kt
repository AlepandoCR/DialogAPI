package alepando.dev.dialogapi.factory.input

import net.minecraft.network.chat.Component

abstract class LabelVisible<T>(label: Component, with: Int, val labelVisible: Boolean) : SizeableInput<T>(label, with) {
}