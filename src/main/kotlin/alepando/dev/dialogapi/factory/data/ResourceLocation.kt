package alepando.dev.dialogapi.factory.data

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.resources.ResourceLocation

/**
 * Represents a resource location, consisting of a namespace and a path.
 * This is a wrapper around Minecraft's [ResourceLocation].
 *
 * @property namespace The namespace of the resource location.
 * @property path The path of the resource location.
 */
class ResourceLocation(
    private val namespace: String,
    private val path: String
): Wrapper<ResourceLocation>  {
    /**
     * Converts this resource location to its NMS equivalent.
     * @return The NMS [ResourceLocation].
     */
    override fun toNMS(): ResourceLocation { return ResourceLocation.fromNamespaceAndPath(namespace,path) }
}