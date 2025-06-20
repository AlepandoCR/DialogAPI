package alepando.dev.dialogapi.listeners

import alepando.dev.dialogapi.packets.PacketSniffer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.Plugin

internal class ServerStatusListener(private val plugin: Plugin): Listener {
    @EventHandler
    fun onDisable(event: PluginDisableEvent){
        PacketSniffer.cleanOrphanedPipelines(plugin)
    }

    @EventHandler
    fun onEnable(event: PluginEnableEvent){
        PacketSniffer.cleanOrphanedPipelines(plugin)
    }
}