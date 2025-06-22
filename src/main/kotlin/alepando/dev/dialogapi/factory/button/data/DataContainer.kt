package alepando.dev.dialogapi.factory.button.data

import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer
import org.bukkit.craftbukkit.persistence.CraftPersistentDataTypeRegistry
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

/**
 * Utility class for building a [PersistentDataContainer] in a fluent way.
 *
 * This container allows you to programmatically attach key-value pairs to Bukkit objects
 * using the Persistent Data API. It wraps the internal [CraftPersistentDataContainer] to
 * manually populate entries with specific [NamespacedKey], [PersistentDataType], and value.
 *
 * @constructor Creates a new empty persistent data container.
 */
class DataContainer {

    /**
     * The underlying [PersistentDataContainer] where all key-value pairs are stored.
     */
    internal val container: PersistentDataContainer = create()

    /**
     * Adds a new key-value pair to the container.
     *
     * @param key The [NamespacedKey] used to store the value.
     * @param dataType The [PersistentDataType] that defines how the value is stored and retrieved.
     * @param value The actual value to store.
     */
    fun <P, C : Any> add(key: NamespacedKey, dataType: PersistentDataType<P, C>, value: C) {
        container.set(key, dataType, value)
    }

    /**
     * Creates a new instance of [CraftPersistentDataContainer] with a fresh type registry.
     *
     * @return A new empty [PersistentDataContainer].
     */
    private fun create(): PersistentDataContainer {
        return CraftPersistentDataContainer(CraftPersistentDataTypeRegistry())
    }
}
