package alepando.dev.dialogapi.executor.events

import alepando.dev.dialogapi.factory.Dialog
import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.packets.parser.PayloadParser
import alepando.dev.dialogapi.packets.reader.InputReader
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.Plugin

class PlayerOpenDialogEvent(player: Player, val dialog: Dialog): PlayerEvent(player) {

    override fun getHandlers(): HandlerList = handlerList

    companion object {
        @JvmStatic
        private val handlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handlerList
    }
}