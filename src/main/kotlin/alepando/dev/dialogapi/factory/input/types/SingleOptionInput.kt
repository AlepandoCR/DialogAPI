package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.LabelVisible
import alepando.dev.dialogapi.factory.input.options.Entry
import net.minecraft.network.chat.Component

typealias NMSSingleOptionInput = net.minecraft.server.dialog.input.SingleOptionInput
typealias NMSEntry = net.minecraft.server.dialog.input.SingleOptionInput.Entry

/**
 * Represents a single option input field in a dialog.
 * This allows the user to select one option from a list of entries.
 *
 * @property entries The list of [Entry] options for this input.
 */
class SingleOptionInput(
    label: Component,
    with: Int,
    key: String,
    private val entries: List<Entry>, labelVisible: Boolean
) : LabelVisible<NMSSingleOptionInput>(label,key, with, labelVisible), Wrapper<NMSSingleOptionInput> {

    /**
     * Converts this single option input to its NMS equivalent.
     * @return The NMS [NMSSingleOptionInput].
     */
    override fun toNMS(): NMSSingleOptionInput {
        return NMSSingleOptionInput(with,toNMSEntryList(),label,labelVisible)
    }

    /**
     * Converts the list of [Entry] objects to a list of NMS [NMSEntry] objects.
     * @return A list of NMS [NMSEntry] objects.
     */
    private fun toNMSEntryList(): List<NMSEntry>{
        val nmsList = mutableListOf<NMSEntry>()

        for (entry in entries) {
            nmsList.add(entry.toNMS())
        }

        return nmsList
    }
}