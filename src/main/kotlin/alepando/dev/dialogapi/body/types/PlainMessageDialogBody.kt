package alepando.dev.dialogapi.body.types

import alepando.dev.dialogapi.body.DialogBody
import alepando.dev.dialogapi.util.Translator
import net.kyori.adventure.text.Component
import net.minecraft.server.dialog.body.PlainMessage

/**
 * Represents a plain message dialog body.
 *
 * @property contents The contents of the dialog body.
 */
class PlainMessageDialogBody(width: Int, private val contents: Component) : DialogBody<PlainMessage>(width) {
    /**
     * Converts this dialog body to its NMS equivalent.
     * @return The NMS equivalent of this dialog body.
     */
    override fun toNMS(): PlainMessage {
        return PlainMessage(Translator.componentToNMS(contents),width)
    }
}