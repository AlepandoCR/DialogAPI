package alepando.dev.dialogapi.util

import alepando.dev.dialogapi.util.Translator.toPersistentDataContainer
import io.papermc.paper.adventure.PaperAdventure
import net.kyori.adventure.text.Component
import net.minecraft.nbt.*
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer
import org.bukkit.craftbukkit.persistence.CraftPersistentDataTypeRegistry
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import net.minecraft.network.chat.Component as NMSComponent

/**
 * Utility object for translating Adventure [Component] instances to NMS (Net Minecraft Server) [NMSComponent] instances.
 */
internal object Translator {

    /**
     * Converts an Adventure [Component] to its NMS equivalent using Paper's Adventure library.
     *
     * @param component The Adventure [Component] to convert.
     * @return The corresponding NMS [NMSComponent].
     */
    fun componentToNMS(component: Component): NMSComponent {
        return PaperAdventure.asVanilla(component)
    }


    fun PersistentDataContainer.toCompoundTag(): CompoundTag {
        val tag = CompoundTag()

        for (key in this.keys) {
            val name = key.key

            when {
                this.has(key, PersistentDataType.BYTE) -> {
                    val value = this.get(key, PersistentDataType.BYTE)!!
                    tag.putByte(name, value)
                }

                this.has(key, PersistentDataType.SHORT) -> {
                    val value = this.get(key, PersistentDataType.SHORT)!!
                    tag.putShort(name, value)
                }

                this.has(key, PersistentDataType.INTEGER) -> {
                    val value = this.get(key, PersistentDataType.INTEGER)!!
                    tag.putInt(name, value)
                }

                this.has(key, PersistentDataType.LONG) -> {
                    val value = this.get(key, PersistentDataType.LONG)!!
                    tag.putLong(name, value)
                }

                this.has(key, PersistentDataType.FLOAT) -> {
                    val value = this.get(key, PersistentDataType.FLOAT)!!
                    tag.putFloat(name, value)
                }

                this.has(key, PersistentDataType.DOUBLE) -> {
                    val value = this.get(key, PersistentDataType.DOUBLE)!!
                    tag.putDouble(name, value)
                }

                this.has(key, PersistentDataType.STRING) -> {
                    val value = this.get(key, PersistentDataType.STRING)!!
                    tag.putString(name, value)
                }

                this.has(key, PersistentDataType.BYTE_ARRAY) -> {
                    val value = this.get(key, PersistentDataType.BYTE_ARRAY)!!
                    tag.putByteArray(name, value)
                }

                this.has(key, PersistentDataType.INTEGER_ARRAY) -> {
                    val value = this.get(key, PersistentDataType.INTEGER_ARRAY)!!
                    tag.putIntArray(name, value)
                }

                this.has(key, PersistentDataType.LONG_ARRAY) -> {
                    val value = this.get(key, PersistentDataType.LONG_ARRAY)!!
                    tag.putLongArray(name, value)
                }

                this.has(key, PersistentDataType.TAG_CONTAINER) -> {
                    val value = this.get(key, PersistentDataType.TAG_CONTAINER)!!
                    tag.put(name, value.toCompoundTag())
                }

                this.has(key, PersistentDataType.TAG_CONTAINER_ARRAY) -> {
                    val list = this.get(key, PersistentDataType.TAG_CONTAINER_ARRAY)!!
                    val listTag = ListTag()

                    list.forEach { container ->
                        listTag.add(container.toCompoundTag())
                    }

                    tag.put(name, listTag)
                }

                else -> {
                    System.err.println("Unknown or unsupported PersistentDataType for key: $key")
                }
            }
        }

        return tag
    }

    fun CompoundTag.toPersistentDataContainer(
        plugin:Plugin
    ): PersistentDataContainer {
        val container = CraftPersistentDataContainer(CraftPersistentDataTypeRegistry())
        for (entrySet in this.entrySet()) {
            val name = entrySet.key
            val base = entrySet.value
            val key = NamespacedKey(plugin, name)

            when (base) {
                is ByteTag -> container.set(key, PersistentDataType.BYTE, base.value)
                is ShortTag -> container.set(key, PersistentDataType.SHORT, base.value)
                is IntTag -> container.set(key, PersistentDataType.INTEGER, base.value)
                is LongTag -> container.set(key, PersistentDataType.LONG, base.value)
                is FloatTag -> container.set(key, PersistentDataType.FLOAT, base.value)
                is DoubleTag -> container.set(key, PersistentDataType.DOUBLE, base.value)
                is StringTag -> container.set(key, PersistentDataType.STRING, base.value)
                is ByteArrayTag -> container.set(key, PersistentDataType.BYTE_ARRAY, base.asByteArray)
                is IntArrayTag -> container.set(key, PersistentDataType.INTEGER_ARRAY, base.asIntArray)
                is LongArrayTag -> container.set(key, PersistentDataType.LONG_ARRAY, base.asLongArray)

                is CompoundTag -> {
                    val nested = container.adapterContext.newPersistentDataContainer()
                    base.toPersistentDataContainer(plugin)
                    container.set(key, PersistentDataType.TAG_CONTAINER, nested)
                }

                is ListTag -> {
                    if (base.all { it is CompoundTag }) {
                        val containers = base.map {
                            val nested = container.adapterContext.newPersistentDataContainer()
                            (it as CompoundTag).toPersistentDataContainer(plugin)
                            nested
                        }.toTypedArray()
                        container.set(key, PersistentDataType.TAG_CONTAINER_ARRAY, containers)
                    }
                }

                else -> System.err.println("Unsupported NBT tag type: ${base::class.simpleName} for key $name")
            }
        }

        return container
    }
}

