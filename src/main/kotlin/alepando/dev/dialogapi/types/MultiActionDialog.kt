package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.server.dialog.ActionButton
import net.minecraft.server.dialog.MultiActionDialog
import java.util.*

typealias NMSMultiActionDialog = MultiActionDialog

class MultiActionDialog(
    data: DialogData,
    private val buttons: List<Button>,
    private val exitButton: Optional<Button>,
    private val columns: Int

): Dialog(data) {
    override fun toNMS(): NMSMultiActionDialog {
        return NMSMultiActionDialog(data.toNMS(),toNMSButtonList(),toNMSOptionalButton(),columns)
    }

    private fun toNMSButtonList(): List<ActionButton>{
        val nmsList = mutableListOf<ActionButton>()

        for (button in buttons) {
            nmsList.add(button.toNMS())
        }

        return nmsList
    }

    private fun toNMSOptionalButton(): Optional<ActionButton>{
        var nmsOptional = Optional.empty<ActionButton>()

        if (exitButton.isPresent) nmsOptional = Optional.of(exitButton.get().toNMS())

        return nmsOptional
    }
}