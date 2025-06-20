package alepando.dev.dialogapi.factory.actions

import alepando.dev.dialogapi.util.DynamicListener
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * Abstract class representing a custom action that can be executed.
 * Custom actions can include tasks and optional event listeners.
 */
abstract class CustomAction {

    /**
     * Optional [DynamicListener] that can be used to register and unregister
     * Bukkit event listeners associated with this action.
     */
    var dynamicListener: DynamicListener? = null

    /**
     * Registers the listener provided by the [listener] method with the [dynamicListener].
     */
    private fun registerListener(){
        val listener = listener()
        listener?: return
        dynamicListener?.setListener(listener)
    }

    /**
     * The main task to be executed by this action.
     * This method must be implemented by subclasses.
     *
     * @param player The player for whom the action is being executed.
     * @param plugin The plugin instance.
     */
    protected abstract fun task(player: Player,plugin: Plugin)

    /**
     * Executes the custom action.
     * This involves initializing the [dynamicListener], registering any associated listener,
     * and running the [task] asynchronously.
     *
     * @param player The player for whom the action is being executed.
     * @param plugin The plugin instance.
     */
    fun execute(player: Player,plugin: Plugin){
        dynamicListener = DynamicListener(plugin)
        registerListener()
        object : BukkitRunnable(){
            override fun run() {
                task(player,plugin)
            }
        }.runTask(plugin)
    }

    /**
     * Provides an optional [Listener] to be registered while this action is active.
     * Subclasses can override this method to provide a specific listener.
     *
     * @return A [Listener] instance or null if no listener is needed.
     */
    open fun listener(): Listener?{
        return null
    }

}