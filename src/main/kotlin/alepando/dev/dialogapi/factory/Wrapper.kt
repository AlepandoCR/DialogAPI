package alepando.dev.dialogapi.factory

/**
 * Interface for wrapping an object to its NMS (Net Minecraft Server) equivalent.
 * @param T The type of the NMS object.
 */
interface Wrapper<T> {
    /**
     * Converts this object to its NMS equivalent.
     * @return The NMS equivalent of this object.
     */
    fun toNMS(): T
}