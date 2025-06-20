package alepando.dev.dialogapi.packets.reader

import alepando.dev.dialogapi.executor.CustomKeyRegistry // Import new registry
import alepando.dev.dialogapi.packets.parser.PayloadParser
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

/**
 * Internal object responsible for managing the processing of incoming dialog packets.
 * It uses the [CustomKeyRegistry] to find the appropriate action or input reader
 * based on the packet's ID.
 */
internal object ReaderManager {

    /**
     * Processes a [ServerboundCustomClickActionPacket] for input data.
     * It retrieves the corresponding [InputReader] from the [CustomKeyRegistry]
     * and executes its task with the parsed payload.
     *
     * @param player The player who sent the packet.
     * @param packet The packet to process.
     */
    fun peekInputs(player: Player, packet: ServerboundCustomClickActionPacket) {
        val binding = CustomKeyRegistry.getBinding(packet.id)
        binding?.reader?.task(player, PayloadParser.getValue(packet))
    }

    /**
     * Processes a [ServerboundCustomClickActionPacket] for actions.
     * It retrieves the corresponding [CustomAction] from the [CustomKeyRegistry]
     * and executes it.
     *
     * @param player The player who sent the packet.
     * @param packet The packet to process.
     * @param plugin The plugin instance, required for executing the action.
     */
    fun peekActions(player: Player, packet: ServerboundCustomClickActionPacket, plugin: Plugin) {
        val binding = CustomKeyRegistry.getBinding(packet.id)
        binding?.action?.execute(player, plugin)
    }

}
