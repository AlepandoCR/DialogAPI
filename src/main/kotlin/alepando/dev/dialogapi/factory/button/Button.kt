package alepando.dev.dialogapi.factory.button

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.button.data.Action
import alepando.dev.dialogapi.factory.button.data.ButtonData
import net.minecraft.server.dialog.ActionButton
import java.util.*

class Button(
    private val data: ButtonData,
    private val action: Optional<Action> = Optional.empty()
): Wrapper<ActionButton> {
    override fun toNMS(): ActionButton{
        if(action.isEmpty) return ActionButton(data.toNMS(), Optional.empty())
        return ActionButton(data.toNMS(),action.get().toNMS())
    }
}