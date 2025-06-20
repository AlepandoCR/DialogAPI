package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.CommonButtonData
import java.util.*

class ButtonData(
    private val label: Component,
    private val width: Int,
    private val tooltip: Optional<Component> = Optional.empty()
): Wrapper<CommonButtonData> {
    override fun toNMS(): CommonButtonData{
        return CommonButtonData(label,tooltip,width)
    }
}