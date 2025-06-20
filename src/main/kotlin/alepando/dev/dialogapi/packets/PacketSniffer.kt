package alepando.dev.dialogapi.packets

import alepando.dev.dialogapi.packets.reader.ReaderManager
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.Connection
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*

/**
 * Internal object responsible for injecting and ejecting packet sniffers into player channels.
 * This allows the API to intercept and process specific packets for dialog interactions.
 */
internal object PacketSniffer {

    /**
     * Set of UUIDs of players who currently have a packet sniffer injected.
     */
    private val injectedPlayers = mutableSetOf<UUID>()

    /**
     * Injects a packet sniffer into the player's network channel.
     * The sniffer listens for [ServerboundCustomClickActionPacket] packets
     * and passes them to the [ReaderManager] for processing.
     *
     * @param player The player to inject the sniffer into.
     * @param plugin The plugin instance.
     */
    fun inject(player: Player, plugin: Plugin) {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection: Connection = nmsPlayer.connection.connection
        val channel = connection.channel

//      if (channel.pipeline().get("custom_click_sniffer") != null) return

        val handler = object : ChannelDuplexHandler() {
            /**
             * Called when a packet is read from the channel.
             * If the packet is a [ServerboundCustomClickActionPacket], it's processed by the [ReaderManager].
             */
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                if (msg is ServerboundCustomClickActionPacket) {
                    ReaderManager.peekActions(player, msg, plugin)
                    ReaderManager.peekInputs(player, msg)
                }
                super.channelRead(ctx, msg)
            }
        }

        channel.pipeline().addBefore("packet_handler", "custom_click_sniffer", handler)
        injectedPlayers.add(player.uniqueId)
    }

    /**
     * Ejects the packet sniffer from the player's network channel.
     *
     * @param player The player to eject the sniffer from.
     */
    fun eject(player: Player) {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection = nmsPlayer.connection.connection
        val channel = connection.channel

        channel.pipeline().get("custom_click_sniffer")?.let {
            channel.pipeline().remove("custom_click_sniffer")
            injectedPlayers.remove(player.uniqueId)
        }
    }

    /**
     * Ejects packet sniffers from all currently online players.
     * This is typically used during plugin shutdown to clean up resources.
     */
    fun unregisterAll() {
        Bukkit.getOnlinePlayers().forEach { eject(it) }
        injectedPlayers.clear()
    }
}
