package alepando.dev.dialogapi

import alepando.dev.dialogapi.listeners.PlayerConnectionStatus
import alepando.dev.dialogapi.listeners.ServerStatusListener
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

/**
 * Initializes the Dialog API internals.
 */
object DialogAPI {

    private var initialized = false

    /**
     * Initializes the Dialog API by registering necessary listeners and hooks.
     *
     * This method should be called once, ideally during the plugin's onEnable.
     */
    fun initialize(plugin: Plugin) {
        if (initialized) return
        initialized = true

        val pm: PluginManager = Bukkit.getPluginManager()
        pm.registerEvents(PlayerConnectionStatus(plugin), plugin)
        pm.registerEvents(ServerStatusListener(plugin),plugin)
    }


}
