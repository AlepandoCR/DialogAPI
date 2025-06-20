package alepando.dev.dialogapi.types.builders

import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.types.NoticeDialog

class NoticeDialogBuilder {
    private lateinit var data: DialogData
    private lateinit var button: Button

    fun data(data: DialogData) = apply { this.data = data }
    fun button(button: Button) = apply { this.button = button }

    fun build(): NoticeDialog {
        return NoticeDialog(data, button)
    }
}
