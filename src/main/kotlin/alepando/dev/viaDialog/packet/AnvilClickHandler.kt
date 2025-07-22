package alepando.dev.viaDialog.packet

import alepando.dev.dialogapi.DialogAPI
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket
import net.minecraft.server.level.ServerPlayer
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

class AnvilClickHandler(
    private val player: Player,
    private val onRename: (String) -> Unit
) : ChannelDuplexHandler() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is ServerboundContainerClickPacket) {
            if (msg.slotNum == 2.toShort()) {
                val nmsPlayer = (player as CraftPlayer).handle as ServerPlayer
                val container = nmsPlayer.containerMenu
                val item = container.getSlot(2).item

                val rename = item.hoverName.string
                Bukkit.getScheduler().runTask(DialogAPI.plugin!!, Runnable {
                    DialogAPI.log("[AnvilClickHandler] Rename captured: $rename")
                    onRename(rename)
                })
                ctx.pipeline().remove("anvil_input_listener_${player.uniqueId}")
            }else{
                DialogAPI.log("Slot not 2, slot: ${msg.slotNum}")
            }
        }

        super.channelRead(ctx, msg)
    }
}
