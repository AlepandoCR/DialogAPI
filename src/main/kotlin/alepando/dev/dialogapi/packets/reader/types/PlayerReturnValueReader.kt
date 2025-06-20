package alepando.dev.dialogapi.packets.reader.types

import alepando.dev.dialogapi.packets.reader.InputReader
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.entity.Player

/**
 * A simple implementation of [InputReader] that primarily serves as an example
 * or a basic handler for dialog interactions where the returned value is of interest.
 * This reader, by default, sends a message to the player with the string representation of the received value.
 *
 * It can be used for custom dialog components that return simple data like strings, numbers, or booleans
 * when no more complex client-side data processing is needed by the [InputReader] itself.
 */
object PlayerReturnValueReader: InputReader {
    /**
     * Handles the received packet by sending a message to the player containing the string
     * representation of the extracted value.
     *
     * @param player The Bukkit [Player] who interacted with the dialog.
     * @param value The value extracted from the packet's payload.
     */
    override fun task(player: Player, value: Any?) {
        player.sendMessage("In: $value")
    }
}