package alepando.dev.versionSupplier.packet

import alepando.dev.dialogapi.DialogAPI
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.Connection
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

    fun injectPlayer(player: Player, plugin: Plugin) {
        val channel = (player as CraftPlayer).handle.connection.connection.channel
        val handlerName = "${plugin.name.lowercase()}_version_sniffer"

        if (channel.pipeline().get(handlerName) == null) {
            channel.pipeline().addBefore("packet_handler", handlerName, object : ChannelDuplexHandler() {
                override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
                    if (msg is ByteBuf) {
                        val readerIndex = msg.readerIndex()
                        try {
                            val packetId = readVarInt(msg)
                            if (packetId == 0) { // Handshake packet
                                val protocolVersion = readVarInt(msg)
                                readString(msg) // hostname
                                msg.readUnsignedShort() // port
                                readVarInt(msg) // intention
                                channelProtocols[ctx] = protocolVersion
                            }
                        } catch (_: Exception) {
                        } finally {
                            msg.readerIndex(readerIndex)
                        }
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
        }, 1L) // Delay 1 tick para asegurar que el handshake haya ocurrido
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

    private fun readVarInt(buf: ByteBuf): Int {
        var numRead = 0
        var result = 0
        var read: Byte
        do {
            read = buf.readByte()
            val value = (read.toInt() and 0b01111111)
            result = result or (value shl (7 * numRead))
            numRead++
            if (numRead > 5) throw RuntimeException("VarInt too big")
        } while ((read.toInt() and 0b10000000) != 0)
        return result
    }

    private fun readString(buf: ByteBuf): String {
        val length = readVarInt(buf)
        val bytes = ByteArray(length)
        buf.readBytes(bytes)
        return String(bytes, Charsets.UTF_8)
    }
}
