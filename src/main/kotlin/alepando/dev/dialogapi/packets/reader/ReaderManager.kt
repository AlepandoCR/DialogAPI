package alepando.dev.dialogapi.packets.reader

import alepando.dev.dialogapi.executor.CustomKeyRegistry // Import new registry
import alepando.dev.dialogapi.packets.parser.PayloadParser
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

internal object ReaderManager {

    fun peekInputs(player: Player, packet: ServerboundCustomClickActionPacket) {
        val binding = CustomKeyRegistry.getBinding(packet.id)
        binding?.reader?.task(player, PayloadParser.getValue(packet))
    }

    fun peekActions(player: Player, packet: ServerboundCustomClickActionPacket, plugin: Plugin) {
        val binding = CustomKeyRegistry.getBinding(packet.id)
        binding?.action?.execute(player, plugin)
    }

}
