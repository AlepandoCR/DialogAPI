package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.MultiActionDialog
import java.util.*

class MultiActionDialogBuilder {
    private lateinit var data: DialogData
    private var buttons: MutableList<Button> = mutableListOf()
    private var exitButton: Optional<Button> = Optional.empty()
    private var columns: Int = 1

    fun data(data: DialogData) = apply { this.data = data }
    fun buttons(buttons: List<Button>) = apply { this.buttons = buttons.toMutableList() }
    fun addButton(button: Button) = apply { this.buttons.add(button) }
    fun exitButton(exitButton: Button?) = apply { this.exitButton = Optional.ofNullable(exitButton) }
    fun columns(columns: Int) = apply { this.columns = columns }

    fun build(): MultiActionDialog {
        return MultiActionDialog(data, buttons, exitButton, columns)
    }
}
