package alepando.dev.dialogapi.packets.parser

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

        for(key in compound.keySet()){
            val tag = compound.get(key) ?: continue
            val type = getTypedValue(tag) ?: continue
            val data = type.get() ?: continue

            list.add(InputValue(data,key))
        }

        return list
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

    fun toCompoundTag(values: InputValueList): CompoundTag {
        val compound = CompoundTag()
        for (value in values.list) {
            val tag = when (val data = value.value) {
                is Byte -> ByteTag.valueOf(data)
                is Short -> ShortTag.valueOf(data)
                is Int -> IntTag.valueOf(data)
                is Long -> LongTag.valueOf(data)
                is Float -> FloatTag.valueOf(data)
                is Double -> DoubleTag.valueOf(data)
                is String -> StringTag.valueOf(data)
                is ByteArray -> ByteArrayTag(data)
                is IntArray -> IntArrayTag(data)
                is LongArray -> LongArrayTag(data)
                is List<*> -> {
                    val tagList = ListTag()
                    data.forEach { item ->
                        when (item) {
                            is String -> tagList.add(StringTag.valueOf(item))
                            is Int -> tagList.add(IntTag.valueOf(item))
                            is Byte -> tagList.add(ByteTag.valueOf(item))
                            is Short -> tagList.add(ShortTag.valueOf(item))
                            is Long -> tagList.add(LongTag.valueOf(item))
                            is Float -> tagList.add(FloatTag.valueOf(item))
                            is Double -> tagList.add(DoubleTag.valueOf(item))
                        }
                    }
                    tagList
                }

                is Map<*, *> -> {
                    val subCompound = CompoundTag()
                    data.forEach { (k, v) ->
                        if (k is String && v is String) {
                            subCompound.put(k, StringTag.valueOf(v))
                        }
                    }
                    subCompound
                }

                else -> {
                    Bukkit.getLogger().warning("Unsupported input value type: ${data.javaClass.simpleName}")
                    null
                }
            }

            if (tag != null) compound.put(value.key, tag)
        }
        return compound
    }

}
