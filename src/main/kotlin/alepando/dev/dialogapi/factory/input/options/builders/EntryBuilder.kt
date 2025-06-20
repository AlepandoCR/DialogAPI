package alepando.dev.dialogapi.factory.input.options.builders

import alepando.dev.dialogapi.factory.input.options.Entry
import net.minecraft.network.chat.Component
import java.util.*

class EntryBuilder {
    private var id: String = ""
    private var initial: Boolean = false
    private var display: Optional<Component> = Optional.empty()

    fun id(id: String) = apply { this.id = id }
    fun initial(initial: Boolean) = apply { this.initial = initial }
    fun display(display: Component?) = apply { this.display = Optional.ofNullable(display) }

    fun build(): Entry {
        return Entry(id, initial, display)
    }
}
