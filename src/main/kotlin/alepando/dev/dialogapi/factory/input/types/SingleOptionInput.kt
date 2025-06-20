package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.LabelVisible
import alepando.dev.dialogapi.factory.input.options.Entry
import net.minecraft.network.chat.Component

typealias NMSSingleOptionInput = net.minecraft.server.dialog.input.SingleOptionInput
typealias NMSEntry = net.minecraft.server.dialog.input.SingleOptionInput.Entry

class SingleOptionInput(
    label: Component,
    with: Int,
    private val entries: List<Entry>, labelVisible: Boolean
) : LabelVisible<NMSSingleOptionInput>(label, with, labelVisible), Wrapper<NMSSingleOptionInput> {

    override fun toNMS(): NMSSingleOptionInput {
        return NMSSingleOptionInput(with,toNMSEntryList(),label,labelVisible)
    }

    private fun toNMSEntryList(): List<NMSEntry>{
        val nmsList = mutableListOf<NMSEntry>()

        for (entry in entries) {
            nmsList.add(entry.toNMS())
        }

        return nmsList
    }
}