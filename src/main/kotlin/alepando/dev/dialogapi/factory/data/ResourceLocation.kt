package alepando.dev.dialogapi.factory.data

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.resources.ResourceLocation as NMSResourceLocation

/**
 * Represents a resource location, consisting of a namespace and a path.
 * This is a wrapper around Minecraft's [NMSResourceLocation].
 *
 * @property namespace The namespace of the resource location.
 * @property path The path of the resource location.
 */
class ResourceLocation(
    private val namespace: String,
    private val path: String
): Wrapper<NMSResourceLocation>  {
    /**
     * Converts this resource location to its NMS equivalent.
     * @return The NMS [NMSResourceLocation].
     */
    override fun toNMS(): NMSResourceLocation { return NMSResourceLocation.fromNamespaceAndPath(namespace,path) }

    companion object {
        fun fromNMS(location: NMSResourceLocation): ResourceLocation {
            return ResourceLocation(location.namespace, location.path)
        }
    }
}