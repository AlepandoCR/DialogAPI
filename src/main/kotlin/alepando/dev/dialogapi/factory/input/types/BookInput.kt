package alepando.dev.dialogapi.factory.input.types

import alepando.dev.dialogapi.factory.input.Input
import alepando.dev.dialogapi.util.Translator.toNMS
import net.kyori.adventure.text.Component


class BookInput(
    key: String,
    label: String
) : Input<String>(Component.text(label).toNMS(), key) {
    override fun toNMS(): String {
        return ""
    }
}
