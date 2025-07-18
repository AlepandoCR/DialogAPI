package alepando.dev.dialogapi.factory.button

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.data.ButtonData
import alepando.dev.dialogapi.factory.button.data.KeyedAction
import net.minecraft.server.dialog.ActionButton
import java.util.*


/**
 * Represents a button in a dialog.
 *
 * @property data The data for this button.
 * @property action The action to perform when this button is clicked.
 */
class Button(
    val data: ButtonData,
    val action: Optional<KeyedAction> = Optional.empty()
): Wrapper<ActionButton> {
    /**
     * Converts this button to its NMS equivalent.
     * @return The NMS equivalent of this button.
     */
    override fun toNMS(): ActionButton{
        if(action.isEmpty) return ActionButton(data.toNMS(), Optional.empty())
        return ActionButton(data.toNMS(),action.get().toNMS())
    }

    companion object {
        fun fromNMS(button: ActionButton): Button {
            val buttonData = ButtonData.fromNMS(button.button)
            val keyedAction = if (button.action.isPresent) Optional.of(KeyedAction.fromNMS(button.action.get())) else Optional.empty()
            return Button(buttonData, keyedAction)
        }
    }
}