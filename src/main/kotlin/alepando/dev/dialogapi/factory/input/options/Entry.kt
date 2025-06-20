package alepando.dev.dialogapi.factory.input.options

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.input.SingleOptionInput
import java.util.*

typealias NMSEntry = SingleOptionInput.Entry

class Entry(
    private val id: String,
    private val initial: Boolean,
    private val display: Optional<Component> = Optional.empty()
): Wrapper<NMSEntry> {
    override fun toNMS(): NMSEntry {
        return NMSEntry(id,display,initial)
    }
}