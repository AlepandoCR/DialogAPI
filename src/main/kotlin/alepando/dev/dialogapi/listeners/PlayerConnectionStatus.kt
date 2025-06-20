package alepando.dev.dialogapi.listeners

import alepando.dev.dialogapi.packets.PacketSniffer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class PlayerConnectionStatus(private val plugin: Plugin): Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        PacketSniffer.inject(event.player, plugin)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent){
        PacketSniffer.eject(event.player)
    }
}