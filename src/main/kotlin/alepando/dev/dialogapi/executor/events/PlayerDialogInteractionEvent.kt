package alepando.dev.dialogapi.executor.events

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.packets.parser.PayloadParser
import alepando.dev.dialogapi.packets.reader.InputReader
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.Plugin

class PlayerDialogInteractionEvent(player: Player, packet: ServerboundCustomClickActionPacket, internal val plugin: Plugin = DialogAPI.plugin!!): PlayerEvent(player) {

    private val payload = PayloadParser.getValues(packet)
    val id = packet.id

    override fun getHandlers(): HandlerList = handlerList

    fun read(reader:InputReader){
        reader.task(player,payload)
    }

    fun action(action:CustomAction){
        action.execute(player,plugin)
    }

    companion object {
        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handlerList
    }
}