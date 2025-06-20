package alepando.dev.dialogapi.factory.data

import alepando.dev.dialogapi.factory.Wrapper
import net.minecraft.resources.ResourceLocation

class ResourceLocation(
    private val namespace: String,
    private val path: String
): Wrapper<ResourceLocation>  {
    override fun toNMS(): ResourceLocation { return ResourceLocation.fromNamespaceAndPath(namespace,path) }
}