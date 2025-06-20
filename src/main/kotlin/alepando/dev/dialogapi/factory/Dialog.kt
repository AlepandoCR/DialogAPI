package alepando.dev.dialogapi.factory

import alepando.dev.dialogapi.factory.data.DialogData
import alepando.dev.dialogapi.util.DynamicListener
import net.minecraft.server.dialog.Dialog
import org.bukkit.event.Listener
import java.util.*

/**
 * Base class for all dialog types within the API.
 * It encapsulates common dialog functionalities and properties.
 *
 * @property data The [DialogData] instance containing all configuration for this dialog.
 * @param dynamicListener An optional [DynamicListener] that can be associated with this dialog
 *                        for handling specific Bukkit events while the dialog is active or relevant.
 */
abstract class Dialog(
    val data: DialogData,
    private val dynamicListener: Optional<DynamicListener> = Optional.empty()
): Wrapper<Dialog> {
    /**
     * Retrieves the Bukkit [Listener] associated with this dialog, if any.
     * This listener might be provided by the [DynamicListener].
     *
     * @return The Bukkit [Listener], or null if no specific listener is set up
     *         or if the [DynamicListener] is not present or has no listener.
     */
    fun getBukkitListener(): Listener?{
        if(dynamicListener.isPresent && dynamicListener.get().isPresent()) {
            return dynamicListener.get().getListener()
        }
        return null
    }

    /**
     * Returns the [Optional] [DynamicListener] associated with this dialog.
     * The [DynamicListener] can be used to manage Bukkit event listeners dynamically
     * based on the dialog's lifecycle or interactions.
     *
     * @return An [Optional] containing the [DynamicListener] if one was provided,
     *         otherwise an empty [Optional].
     */
    fun getDynamicListener(): Optional<DynamicListener> {
        return dynamicListener
    }
}