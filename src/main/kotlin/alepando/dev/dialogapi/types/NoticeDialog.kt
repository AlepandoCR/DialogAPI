package alepando.dev.dialogapi.types

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.Button
import alepando.dev.dialogapi.factory.data.DialogData
import net.minecraft.server.dialog.NoticeDialog

typealias NMSNoticeDialog = NoticeDialog

class NoticeDialog(
    data: DialogData,
    private val button: Button
) : Dialog(data) {

    override fun toNMS(): NMSNoticeDialog {
        return NMSNoticeDialog(
            data.toNMS(),
            button.toNMS()
        )
    }
}
