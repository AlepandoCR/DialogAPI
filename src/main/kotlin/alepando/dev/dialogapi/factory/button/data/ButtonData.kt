package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.CommonButtonData
import java.util.*

/**
 * Represents the data for a button.
 *
 * @property label The label of the button.
 * @property width The width of the button.
 * @property tooltip The tooltip of the button.
 */
class ButtonData(
    private val label: Component,
    private val width: Int,
    private val tooltip: Optional<Component> = Optional.empty()
): Wrapper<CommonButtonData> {
    /**
     * Converts this button data to its NMS equivalent.
     * @return The NMS equivalent of this button data.
     */
    override fun toNMS(): CommonButtonData{
        return CommonButtonData(label,tooltip,width)
    }
}