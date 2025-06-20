package alepando.dev.dialogapi.packets.reader

import alepando.dev.dialogapi.packets.parser.PayloadParser
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player

/**
 * Interface for processing data received from custom dialog interactions.
 * Implementations of this interface are responsible for handling the specific logic
 * when a player interacts with a dialog component that sends a [ServerboundCustomClickActionPacket].
 *
 * This interface is intended to be used with the [alepando.dev.dialogapi.executor.CustomKeyRegistry]
 * to associate custom interaction keys ([net.minecraft.resources.ResourceLocation]) with specific data processing logic.
 */
interface InputReader {
    /**
     * Called when a custom dialog interaction packet is received for a registered key.
     * This method should contain the logic to handle the data sent by the client.
     *
     * @param player The Bukkit [Player] who interacted with the dialog.
     * @param value The value extracted from the packet's payload by [PayloadParser.getValue].
     *              This is provided as a convenience, but implementations can choose to parse the packet directly.
     *              The type of this value depends on the data sent by the client (e.g., String, Int, Boolean).
     */
    fun task(player: Player, value: Any?)
}