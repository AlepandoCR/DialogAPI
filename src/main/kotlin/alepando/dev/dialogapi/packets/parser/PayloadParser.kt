package alepando.dev.dialogapi.packets.parser

import net.minecraft.nbt.*
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import org.bukkit.Bukkit
import java.util.Optional

internal object PayloadParser {
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

    @Suppress("unused")
    fun getValueWithKey(packet: ServerboundCustomClickActionPacket): Pair<String, Any?>? {
        val payloadHolder = packet.payload
        if (payloadHolder.isEmpty) return null

        val compound = payloadHolder.get() as? CompoundTag ?: return null
        val key = compound.keySet().firstOrNull() ?: return null
        val tag = compound.get(key) ?: return null

        return key to getTypedValue(tag)
    }

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
