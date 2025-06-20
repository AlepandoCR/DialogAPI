package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.ListDialog
import java.util.*

class ListDialogBuilder {
    private lateinit var data: DialogData
    private var dialogs: MutableList<net.minecraft.server.dialog.Dialog> = mutableListOf()
    private var exitButton: Optional<Button> = Optional.empty()
    private var columns: Int = 2
    private var buttonWidth: Int = 150

    fun data(data: DialogData) = apply { this.data = data }
    fun dialogs(dialogs: List<net.minecraft.server.dialog.Dialog>) = apply { this.dialogs = dialogs.toMutableList() }
    fun addDialog(dialog: net.minecraft.server.dialog.Dialog) = apply { this.dialogs.add(dialog) }
    fun exitButton(exitButton: Button?) = apply { this.exitButton = Optional.ofNullable(exitButton) }
    fun columns(columns: Int) = apply { this.columns = columns }
    fun buttonWidth(buttonWidth: Int) = apply { this.buttonWidth = buttonWidth }

    fun build(): ListDialog {
        return ListDialog(data, dialogs, exitButton, columns, buttonWidth)
    }
}
