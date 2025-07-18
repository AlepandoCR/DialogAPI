package alepando.dev.dialogapi

import alepando.dev.dialogapi.listeners.PlayerConnectionStatus
import alepando.dev.dialogapi.listeners.ServerStatusListener
import alepando.dev.versionSupplier.packet.ClientVersionSniffer
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager

/**
 * Initializes the Dialog API internals.
 */
object DialogAPI {

    private var initialized = false

    var plugin: Plugin? = null

    /**
     * Initializes the Dialog API by registering necessary listeners and hooks.
     *
     * This method should be called once, ideally during the plugin's onEnable.
     */
    fun initialize(plugin: Plugin) {
        if (initialized) return
        initialized = true

        this.plugin = plugin

        val pm: PluginManager = Bukkit.getPluginManager()
        pm.registerEvents(ClientVersionSniffer,plugin)
        pm.registerEvents(PlayerConnectionStatus(plugin), plugin)
        pm.registerEvents(ServerStatusListener(plugin),plugin)
    }


}
