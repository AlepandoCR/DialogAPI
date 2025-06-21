package alepando.dev.dialogapi.factory.input.types.builders

import alepando.dev.dialogapi.factory.input.options.Entry
import alepando.dev.dialogapi.factory.input.types.SingleOptionInput
import alepando.dev.dialogapi.util.ComponentTranslator

/**
 * Builder class for creating [SingleOptionInput] instances.
 */
class SingleOptionInputBuilder {
    private var label: AdventureComponent = AdventureComponent.text("N/D")
    private var with: Int = 100
    private var entries: MutableList<Entry> = mutableListOf()
    private var labelVisible: Boolean = true
    private var key: String = "not_defined"

    fun key(key: String) = apply { this.key = key }

    /** Sets the label for the single option input. */
    fun label(label: AdventureComponent) = apply { this.label = label }

    /** Sets the width of the single option input. */
    fun width(width: Int) = apply { this.with = width }

    /** Adds an [Entry] to the list of options. */
    fun addEntry(entry: Entry) = apply { this.entries.add(entry) }

    /** Sets the list of [Entry] options. */
    fun entries(entries: List<Entry>) = apply { this.entries = entries.toMutableList() }

    /** Sets whether the label is visible. */
    fun labelVisible(visible: Boolean) = apply { this.labelVisible = visible }

    /**
     * Builds the [SingleOptionInput] instance.
     * @return The created [SingleOptionInput].
     */
    fun build(): SingleOptionInput {
        return SingleOptionInput(ComponentTranslator.toNMS(label), with,key, entries, labelVisible)
    }
}
