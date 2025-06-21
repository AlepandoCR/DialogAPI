package alepando.dev.dialogapi.factory.button

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.data.KeyedAction
import alepando.dev.dialogapi.factory.button.data.ButtonData
import net.minecraft.server.dialog.ActionButton
import java.util.*

/**
 * Represents a button in a dialog.
 *
 * @property data The data for this button.
 * @property action The action to perform when this button is clicked.
 */
class Button(
    private val data: ButtonData,
    private val action: Optional<KeyedAction> = Optional.empty()
): Wrapper<ActionButton> {
    /**
     * Converts this button to its NMS equivalent.
     * @return The NMS equivalent of this button.
     */
    override fun toNMS(): ActionButton{
        if(action.isEmpty) return ActionButton(data.toNMS(), Optional.empty())
        return ActionButton(data.toNMS(),action.get().toNMS())
    }
}