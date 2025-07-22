package alepando.dev.viaDialog.packet

import alepando.dev.dialogapi.DialogAPI
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.protocol.game.ServerboundRenameItemPacket
import org.bukkit.Bukkit
import java.util.UUID

class AnvilRenameHandler(
    private val playerUUID: UUID,
    private val onRename: (String) -> Unit
) : ChannelDuplexHandler() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is ServerboundRenameItemPacket) {
            val rename = msg.name
            Bukkit.getScheduler().runTask(DialogAPI.plugin!!, Runnable {

                DialogAPI.log("[AnvilPacketHandler] Rename captured: $rename")
                onRename(rename)

            })
            ctx.pipeline().remove("anvil_input_listener_${playerUUID}")
        }

        super.channelRead(ctx, msg)
    }
}
