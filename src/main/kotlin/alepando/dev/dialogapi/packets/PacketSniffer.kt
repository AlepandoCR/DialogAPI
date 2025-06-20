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

internal object PacketSniffer {

    private val injectedPlayers = mutableSetOf<UUID>()

    fun inject(player: Player, plugin: Plugin) {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection: Connection = nmsPlayer.connection.connection
        val channel = connection.channel

//      if (channel.pipeline().get("custom_click_sniffer") != null) return

        val handler = object : ChannelDuplexHandler() {
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

    fun eject(player: Player) {
        val nmsPlayer = (player as CraftPlayer).handle
        val connection = nmsPlayer.connection.connection
        val channel = connection.channel

        channel.pipeline().get("custom_click_sniffer")?.let {
            channel.pipeline().remove("custom_click_sniffer")
            injectedPlayers.remove(player.uniqueId)
        }
    }

    fun unregisterAll() {
        Bukkit.getOnlinePlayers().forEach { eject(it) }
        injectedPlayers.clear()
    }
}
