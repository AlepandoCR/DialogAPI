package alepando.dev.dialogapi.factory.actions

import alepando.dev.dialogapi.util.DynamicListener
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * Represents an abstract custom dialog action that can be executed in the context of a player.
 *
 * Custom actions may include executing tasks (such as damaging a player, triggering events, etc.)
 * and optionally registering event listeners during the action’s lifecycle.
 *
 * Extend this class to define new dialog-based actions for the DialogAPI.
 */
abstract class CustomAction {

    /**
     * Optional dynamic event listener used to register and unregister
     * Bukkit event listeners associated with this action at runtime.
     */
    var dynamicListener: DynamicListener? = null

    /**
     * Registers the listener returned by [listener] using the internal [dynamicListener].
     *
     * @param player The [Player] this listener should track or respond to.
     */
    private fun registerListener(player: Player) {
        val listener = listener(player)
        listener ?: return
        dynamicListener?.setListener(listener)
    }

    /**
     * Defines the core task to be executed as part of this custom action.
     * This must be implemented by subclasses to define the specific behavior.
     *
     * @param player The [Player] for whom the action is being executed.
     * @param plugin The plugin instance used for scheduling or context.
     */
    protected abstract fun task(player: Player, plugin: Plugin)

    /**
     * Optionally provides a Bukkit [Listener] that will be dynamically registered
     * for the duration of the action.
     *
     * Override this method in subclasses to add custom event handling logic.
     *
     * @param dialogPlayer The [Player] instance to bind the listener to.
     * @return A [Listener] or `null` if no listener is needed.
     */
    open fun listener(dialogPlayer: Player): Listener? {
        return null
    }

    /**
     * Executes the action:
     * - Initializes the [dynamicListener]
     * - Registers any listener provided by [listener]
     * - Schedules the [task] to run synchronously on the main server thread
     *
     * @param player The [Player] to execute the action for.
     * @param plugin The plugin instance.
     */
    internal fun execute(player: Player, plugin: Plugin) {
        dynamicListener = DynamicListener(plugin)
        registerListener(player)

        object : BukkitRunnable() {
            override fun run() {
                task(player, plugin)
            }
        }.runTask(plugin)
    }
}
