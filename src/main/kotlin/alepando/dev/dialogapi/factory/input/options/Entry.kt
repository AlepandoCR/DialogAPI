package alepando.dev.dialogapi.factory.input.options

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.SingleOptionInput
import java.util.*

typealias NMSEntry = SingleOptionInput.Entry

/**
 * Represents an entry in a single option input.
 *
 * @property id The unique identifier for this entry.
 * @property initial Whether this entry is initially selected.
 * @property display The optional display component for this entry.
 */
class Entry(
    private val id: String,
    private val initial: Boolean,
    private val display: Optional<Component> = Optional.empty()
): Wrapper<NMSEntry> {
    /**
     * Converts this entry to its NMS equivalent.
     * @return The NMS [NMSEntry].
     */
    override fun toNMS(): NMSEntry {
        return NMSEntry(id,display,initial)
    }
}