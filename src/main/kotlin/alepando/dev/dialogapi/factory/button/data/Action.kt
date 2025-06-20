package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.data.ResourceLocation
import net.minecraft.server.dialog.action.Action
import net.minecraft.server.dialog.action.CustomAll
import java.util.*

class Action(
    private val resourceLocation: ResourceLocation
):Wrapper<Optional<Action>> {
    override fun toNMS(): Optional<Action> {
        return Optional.of(CustomAll(resourceLocation.toNMS(), Optional.empty()))
    }

}