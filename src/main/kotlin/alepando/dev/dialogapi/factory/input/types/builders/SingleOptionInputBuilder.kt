package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.options.Entry
import alepando.dev.dialogapi.factory.input.types.SingleOptionInput
import alepando.dev.dialogapi.util.ComponentTranslator
import net.minecraft.network.chat.Component

class SingleOptionInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var with: Int = 100
    private var entries: MutableList<Entry> = mutableListOf()
    private var labelVisible: Boolean = true

    fun label(label: AdventureComponent) = apply { this.label = label }
    fun width(width: Int) = apply { this.with = width }
    fun addEntry(entry: Entry) = apply { this.entries.add(entry) }
    fun entries(entries: List<Entry>) = apply { this.entries = entries.toMutableList() }
    fun labelVisible(visible: Boolean) = apply { this.labelVisible = visible }

    fun build(): SingleOptionInput {
        return SingleOptionInput(ComponentTranslator.toNMS(label), with, entries, labelVisible)
    }
}
