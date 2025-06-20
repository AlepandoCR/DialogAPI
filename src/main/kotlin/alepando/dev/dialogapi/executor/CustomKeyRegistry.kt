package alepando.dev.dialogapi.executor

import alepando.dev.dialogapi.factory.actions.CustomAction
import alepando.dev.dialogapi.packets.reader.InputReader
import alepando.dev.dialogapi.factory.data.ResourceLocation

typealias NMSResourceLocation = net.minecraft.resources.ResourceLocation
/**
 * Internal data class to hold a registered custom action and its corresponding input reader.
 */
internal data class KeyBinding(val action: CustomAction, val reader: InputReader)



/**
 * A registry for developers to register custom actions and input readers,
 * associating them with a unique ResourceLocation (namespace and path).
 */
object CustomKeyRegistry {

    private val registeredKeys = mutableMapOf<NMSResourceLocation, KeyBinding>()

    /**
     * Registers a custom key with an associated action and input reader.
     *
     * @param resourceLocation location to be stored
     * @param action The [CustomAction] to be executed when this key is triggered.
     * @param reader The [InputReader] to process any input associated with this key.
     * @throws IllegalArgumentException if the namespace or path contains invalid characters for ResourceLocation.
     * @throws IllegalStateException if the key (namespace and path combination) is already registered.
     */
    fun register(resourceLocation: ResourceLocation, action: CustomAction, reader: InputReader) {
        val location = try {
            resourceLocation.toNMS()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid namespace or path: '${'$'}namespace:${'$'}path'. Ensure they contain valid characters.", e)
        }

        if (registeredKeys.containsKey(location)) {
            throw IllegalStateException("Custom key '${'$'}location' is already registered.")
        }
        registeredKeys[location] = KeyBinding(action, reader)
    }

    /**
     * Retrieves the [KeyBinding] (action and reader) for a given ResourceLocation.
     * Intended for internal use by the dialog system (e.g., ReaderManager).
     *
     * @param location The [ResourceLocation] of the key.
     * @return The [KeyBinding] if found, otherwise null.
     */
    internal fun getBinding(location: NMSResourceLocation): KeyBinding? {
        return registeredKeys[location]
    }

    /**
     * Clears all registered custom keys.
     * Mainly intended for testing or server shutdown/plugin disable scenarios.
     */
    fun clearAll() {
        registeredKeys.clear()

    }
}
