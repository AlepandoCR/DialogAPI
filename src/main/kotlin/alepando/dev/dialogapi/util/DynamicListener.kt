package alepando.dev.dialogapi.util

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * Manages a Bukkit [Listener] dynamically, allowing it to be registered and unregistered on demand.
 * This is useful for listeners that are only needed for a short period, such as during a dialog interaction.
 *
 * @property plugin The plugin instance used for registering the listener.
 */
class DynamicListener(
    private val plugin: Plugin
) {
    private var activeListener = false
    private var listener: Listener? = null

    /**
     * Unregisters the current listener from Bukkit's event system.
     * Sets [activeListener] to false.
     */
    private fun unRegisterListener(){
        listener?.let {
            HandlerList.unregisterAll(listener!!)
            activeListener = false
        }
    }

    /**
     * Registers the current listener with Bukkit's event system.
     * Sets [activeListener] to true.
     */
    private fun registerListener(){
        listener?.let {
            plugin.server.pluginManager.registerEvents(listener!!,plugin)
            activeListener = true
        }
    }

    /**
     * Checks if the current listener is already registered with Bukkit.
     * @return True if the listener is registered, false otherwise.
     */
    private fun checkIfRegistered(): Boolean {
        for (registeredListener in HandlerList.getRegisteredListeners(plugin)) {
            if (registeredListener.listener == listener) return true
        }
        return false
    }

    /**
     * Unregisters the current listener (if any) and sets it to null.
     */
    fun deleteListener(){
        listener?.let { unRegisterListener() }
        listener = null
    }

    /**
     * Stops (unregisters) the current listener.
     */
    fun stop(){
        unRegisterListener()
    }

    /**
     * Starts (registers) the current listener if it's not already registered.
     */
    fun start(){
        if(checkIfRegistered()) return
        registerListener()
    }

    /**
     * Sets a new [Listener]. If a previous listener was set, it will be unregistered.
     * @param listener The new [Listener] to manage.
     */
    fun setListener(listener: Listener){
        this.listener?.let { unRegisterListener() }

        this.listener = listener
    }

    /**
     * Checks if a listener is currently set.
     * @return True if a listener is set, false otherwise.
     */
    fun isPresent(): Boolean{
        listener ?: return false
        return true
    }

    /**
     * Gets the currently managed [Listener].
     * @return The current [Listener], or null if none is set.
     */
    fun getListener(): Listener?{
        return listener
    }

    /**
     * Checks if the managed listener is currently active (registered).
     * @return True if the listener is active, false otherwise.
     */
    fun isActive(): Boolean{
        return activeListener
    }

    fun stopListenerAfter(time: Long){
        object : BukkitRunnable(){
            override fun run() {
                stop()
            }
        }.runTaskLater(plugin, time)
    }
}