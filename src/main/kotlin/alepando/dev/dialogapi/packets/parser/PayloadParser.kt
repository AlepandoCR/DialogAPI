package alepando.dev.dialogapi.packets.parser

import net.minecraft.nbt.*
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.Bukkit
import java.util.Optional

/**
 * Internal object for parsing the payload of [ServerboundCustomClickActionPacket] packets.
 * It extracts the value from the NBT data within the packet.
 */
internal object PayloadParser {
    /**
     * Extracts the value from the payload of a [ServerboundCustomClickActionPacket].
     * The payload is expected to be a [CompoundTag] with a single entry.
     *
     * @param packet The packet to parse.
     * @return The extracted value, or null if the payload is empty, not a [CompoundTag],
     *         or does not contain a recognizable NBT tag.
     */
    fun getValue(packet: ServerboundCustomClickActionPacket): Any? {
        val payloadHolder = packet.payload
        if (payloadHolder.isEmpty) return null

        val compound = payloadHolder.get() as? CompoundTag ?: return null

        val key = compound.keySet().firstOrNull() ?: return null
        val tag = compound.get(key) ?: return null

        val type = getTypedValue(tag) ?: return null

        val data = type.get()

        return data
    }

    /**
     * Extracts the key-value pair from the payload of a [ServerboundCustomClickActionPacket].
     * The payload is expected to be a [CompoundTag] with a single entry.
     * This function is currently marked with `@Suppress("unused")`.
     *
     * @param packet The packet to parse.
     * @return A [Pair] containing the key and the extracted value, or null if parsing fails.
     */
    @Suppress("unused")
    fun getValueWithKey(packet: ServerboundCustomClickActionPacket): Pair<String, Any?>? {
        val payloadHolder = packet.payload
        if (payloadHolder.isEmpty) return null

        val compound = payloadHolder.get() as? CompoundTag ?: return null
        val key = compound.keySet().firstOrNull() ?: return null
        val tag = compound.get(key) ?: return null

        return key to getTypedValue(tag)
    }

    /**
     * Converts an NBT [Tag] to its corresponding Java type, wrapped in an [Optional].
     *
     * @param tag The NBT [Tag] to convert.
     * @return An [Optional] containing the converted value, or null if the tag type is unknown.
     */
    private fun getTypedValue(tag: Tag): Optional<*>? {
        return when (tag.id.toInt()) {
            1 -> (tag as ByteTag).asByte()
            2 -> (tag as ShortTag).asShort()
            3 -> (tag as IntTag).asInt()
            4 -> (tag as LongTag).asLong()
            5 -> (tag as FloatTag).asFloat()
            6 -> (tag as DoubleTag).asDouble()
            7 -> (tag as ByteArrayTag).asByteArray()
            8 -> (tag as StringTag).asString()
            9 -> (tag as ListTag).asList()
            10 -> (tag as CompoundTag).asCompound()
            11 -> (tag as IntArrayTag).asIntArray()
            12 -> (tag as LongArrayTag).asLongArray()
            else -> {
                Bukkit.getLogger().warning("Unknown NBT tag type: ${tag.id}")
                null
            }
        }
    }

}
