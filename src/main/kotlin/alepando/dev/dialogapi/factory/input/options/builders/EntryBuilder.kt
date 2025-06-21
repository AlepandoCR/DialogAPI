package alepando.dev.dialogapi.factory.input.options.builders

import alepando.dev.dialogapi.factory.input.options.Entry
import alepando.dev.dialogapi.util.ComponentTranslator
import net.kyori.adventure.text.Component
import java.util.*

/**
 * Builder class for creating [Entry] instances.
 */
class EntryBuilder {
    private var id: String = ""
    private var initial: Boolean = false
    private var display: Optional<Component> = Optional.empty()

    /** Sets the ID of the entry. */
    fun id(id: String) = apply { this.id = id }

    /** Sets whether the entry is initially selected. */
    fun initial(initial: Boolean) = apply { this.initial = initial }

    /** Sets the display component of the entry. */
    fun display(display: Component?) = apply { this.display = Optional.ofNullable(display) }

    /**
     * Builds the [Entry] instance.
     * @return The created [Entry].
     */
    fun build(): Entry {
        return Entry(id, initial, Optional.of(ComponentTranslator.toNMS(display.get())))
    }
}
