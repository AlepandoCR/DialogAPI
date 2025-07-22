package alepando.dev.dialogapi.packets.parser

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.util.InputValue
import alepando.dev.dialogapi.util.InputValueList
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
    fun getValues(packet: ServerboundCustomClickActionPacket): InputValueList {
        val payloadHolder = packet.payload
        val list = InputValueList()
        if (payloadHolder.isEmpty) return list

        val compound = payloadHolder.get() as? CompoundTag ?: return list

        for (key in compound.keySet()) {
            val tag = compound.get(key) ?: continue
            var value = fromTag(tag)
            if (value != null) {
                if(value is Optional<*>){
                    if(value.isPresent) value = value.get()!!
                }
                list.add(InputValue(value, key))
            } else {
                DialogAPI.log("Unknown NBT tag type: ${tag.id} for key $key")
            }
        }

        return list
    }

    private fun fromTag(tag: Tag): Any? {
        return when (tag.id.toInt()) {
            1 -> (tag as ByteTag).asByte()
            2 -> (tag as ShortTag).asShort()
            3 -> (tag as IntTag).asInt()
            4 -> (tag as LongTag).asLong()
            5 -> (tag as FloatTag).asFloat()
            6 -> (tag as DoubleTag).asDouble()
            7 -> (tag as ByteArrayTag).asByteArray
            8 -> (tag as StringTag).asString()
            9 -> {
                val listTag = tag as ListTag
                val list = mutableListOf<Any?>()
                for (element in listTag) {
                    list.add(fromTag(element))
                }
                list
            }
            10 -> {
                val compoundTag = tag as CompoundTag
                val map = mutableMapOf<String, Any?>()
                for (key in compoundTag.keySet()) {
                    compoundTag.get(key)?.let { map[key] = fromTag(it) }
                }
                map
            }
            11 -> (tag as IntArrayTag).asIntArray
            12 -> (tag as LongArrayTag).asLongArray
            else -> null
        }
    }

    fun InputValueList.toCompoundTag(): CompoundTag {
        val compound = CompoundTag()
        for (value in this.list) {
            val tag = toTag(value.value)
            if (tag != null) {
                compound.put(value.key, tag)
            } else {
                Bukkit.getLogger().warning("Unsupported input value type: ${value.value.javaClass.simpleName} for key ${value.key}")
            }
        }
        return compound
    }

    private fun toTag(value: Any): Tag? {
        return when (value) {
            is Byte -> ByteTag.valueOf(value)
            is Short -> ShortTag.valueOf(value)
            is Int -> IntTag.valueOf(value)
            is Long -> LongTag.valueOf(value)
            is Float -> FloatTag.valueOf(value)
            is Double -> DoubleTag.valueOf(value)
            is String -> StringTag.valueOf(value)
            is ByteArray -> ByteArrayTag(value)
            is IntArray -> IntArrayTag(value)
            is LongArray -> LongArrayTag(value)
            is List<*> -> {
                val listTag = ListTag()
                value.forEach { item ->
                    if (item != null) {
                        toTag(item)?.let { listTag.add(it) }
                    }
                }
                listTag
            }
            is Map<*, *> -> {
                val compoundTag = CompoundTag()
                value.forEach { (key, value) ->
                    if (key is String && value != null) {
                        toTag(value)?.let { compoundTag.put(key, it) }
                    }
                }
                compoundTag
            }
            else -> null
        }
    }

}
