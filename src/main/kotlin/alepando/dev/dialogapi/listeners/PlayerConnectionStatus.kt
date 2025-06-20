package alepando.dev.dialogapi.listeners

import alepando.dev.dialogapi.packets.PacketSniffer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

/**
 * Listener for player connection events (join and quit).
 * Manages packet sniffing injection and ejection for players.
 *
 * @property plugin The plugin instance.
 */
class PlayerConnectionStatus(private val plugin: Plugin): Listener {

    /**
     * Handles the [PlayerJoinEvent].
     * Injects the packet sniffer for the joining player.
     *
     * @param event The [PlayerJoinEvent].
     */
    @EventHandler
    fun onJoin(event: PlayerJoinEvent){
        PacketSniffer.inject(event.player, plugin)
    }

    /**
     * Handles the [PlayerQuitEvent].
     * Ejects the packet sniffer for the quitting player.
     *
     * @param event The [PlayerQuitEvent].
     */
    @EventHandler
    fun onQuit(event: PlayerQuitEvent){
        PacketSniffer.eject(event.player)
    }
}