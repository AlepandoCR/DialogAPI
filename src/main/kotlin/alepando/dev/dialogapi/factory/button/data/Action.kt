package alepando.dev.dialogapi.factory.button.data

import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.data.ResourceLocation
import net.minecraft.server.dialog.action.Action
import net.minecraft.server.dialog.action.CustomAll
import java.util.*

/**
 * Represents an action associated with a button.
 * This action is identified by a [ResourceLocation].
 *
 * @property resourceLocation The resource location identifying this action.
 */
class Action(
    private val resourceLocation: ResourceLocation
):Wrapper<Optional<Action>> {
    /**
     * Converts this action to its NMS equivalent, wrapped in an [Optional].
     * The NMS action is created as a [CustomAll] action.
     *
     * @return An [Optional] containing the NMS equivalent of this action.
     */
    override fun toNMS(): Optional<Action> {
        return Optional.of(CustomAll(resourceLocation.toNMS(), Optional.empty()))
    }

}