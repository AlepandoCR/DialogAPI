package alepando.dev.dialogapi.factory.data

import alepando.dev.dialogapi.body.DialogBody
import alepando.dev.dialogapi.factory.Wrapper
import alepando.dev.dialogapi.factory.input.Input
import net.minecraft.network.chat.Component
import net.minecraft.server.dialog.CommonDialogData
import net.minecraft.server.dialog.DialogAction
import java.util.*

/** Type alias for the NMS (Net Minecraft Server) DialogBody class. */
typealias NMSDialogBody = net.minecraft.server.dialog.body.DialogBody

/** Type alias for the NMS (Net Minecraft Server) InputControl class. */
typealias NMSInputControl = net.minecraft.server.dialog.input.InputControl

/** Type alias for the NMS (Net Minecraft Server) Input class. */
typealias NMSInput = net.minecraft.server.dialog.Input

/**
 * Holds all the data required to define and display a dialog to a player.
 * This class is typically constructed using [DialogDataBuilder].
 *
 * @property title The main title of the dialog.
 * @property externalTitle An optional title displayed outside the main dialog area, e.g., in the UI frame.
 * @property canCloseWithEscape If true, the dialog can be closed by pressing the Escape key.
 * @property pause If true, the game world will be paused while the dialog is open.
 * @property afterAction The action to perform after the dialog is closed or an action is triggered.
 * @property dialogBody A list of [DialogBody] elements that make up the content of the dialog.
 * @property inputs A list of [Input] elements allowing user interaction.
 */
class DialogData internal constructor( // Constructor is internal, only called by DialogDataBuilder
    private val title: Component,
    private val externalTitle: Optional<Component>,
    private val canCloseWithEscape: Boolean,
    private val pause: Boolean,
    private val afterAction: DialogAction,
    private val dialogBody: List<DialogBody<*>>,
    private val inputs: List<Input<*>>
): Wrapper<CommonDialogData> {

    /**
     * Converts this [DialogData] instance into its corresponding NMS (Net Minecraft Server)
     * [CommonDialogData] representation. This is used internally to interact with the server's dialog system.
     *
     * @return The NMS [CommonDialogData] object.
     */
    override fun toNMS(): CommonDialogData {
        return CommonDialogData(title,externalTitle,canCloseWithEscape,pause,afterAction,nmsBodyList(),nmsInputList())
    }

    private fun nmsInputList():MutableList<NMSInput>{
        val list = mutableListOf<NMSInput>()
        for (input in inputs) {
            val nmsInput = input.toNMS()
            if(nmsInput is NMSInputControl) {
                // TODO: The key "fuchibol" seems like a placeholder and should be configurable or derived.
                val aux = NMSInput("fuchibol",nmsInput)
                list.add(aux)
            }
        }
        return list
    }

    private fun nmsBodyList():MutableList<NMSDialogBody>{
        val list = mutableListOf<NMSDialogBody>()
        for (body in dialogBody) {
            val nmsBody = body.toNMS()
            if(nmsBody is NMSDialogBody) { // Ensure it's the correct NMS type, though toNMS should guarantee this
                list.add(nmsBody)
            }
        }
        return list
    }

}