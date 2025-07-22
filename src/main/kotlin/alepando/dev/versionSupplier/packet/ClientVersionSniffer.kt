package alepando.dev.versionSupplier.packet

import alepando.dev.dialogapi.DialogAPI
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.Connection
import net.minecraft.network.protocol.handshake.ClientIntentionPacket
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal object ClientVersionSniffer : Listener {

    private val channelProtocols = ConcurrentHashMap<ChannelHandlerContext, Int>()
    private val clientProtocols = ConcurrentHashMap<UUID, Int>()

    private fun injectPlayer(player: Player, plugin: Plugin) {
        val channel = (player as CraftPlayer).handle.connection.connection.channel
        val handlerName = "${plugin.name.lowercase()}_version_sniffer"

        if (channel.pipeline().get(handlerName) == null) {
            channel.pipeline().addBefore("packet_handler", handlerName, object : ChannelDuplexHandler() {
                override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                    if (msg is ClientIntentionPacket) {
                        val protocolVersion = msg.protocolVersion
                        channelProtocols[ctx] = protocolVersion
                    }

                    super.channelRead(ctx, msg)
                }
            })
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        injectPlayer(player, DialogAPI.plugin!!)
        Bukkit.getScheduler().runTaskLater(DialogAPI.plugin!!, Runnable {
            val ctx = getContextFromPlayer(player)
            val protocol = getProtocolFromChannel(ctx)
            if (protocol != null) {
                clientProtocols[player.uniqueId] = protocol
                DialogAPI.plugin?.logger?.info("Player: ${player.name} with version $protocol")
            } else {
                DialogAPI.plugin?.logger?.warning("Could not read protocol for ${player.name}")
            }
        }, 1L)
    }

    fun getProtocolVersionForUUID(uuid: UUID): Int? {
        return clientProtocols[uuid]
    }

    fun clear() {
        clientProtocols.clear()
        channelProtocols.clear()
    }

    private fun getProtocolFromChannel(ctx: ChannelHandlerContext?): Int? {
        return ctx?.let { channelProtocols.remove(it) }
    }

    private fun getContextFromPlayer(player: Player): ChannelHandlerContext? {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection: Connection = nmsPlayer.connection.connection
        val channel = connection.channel
        return channel.pipeline().context("packet_handler")
    }
}
