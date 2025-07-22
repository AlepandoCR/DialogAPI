package alepando.dev.dialogapi.packets

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.packets.parser.PayloadParser.toCompoundTag
import alepando.dev.dialogapi.util.InputValueList
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import java.util.*

object PacketBuilder {

    fun build(resourceLocation: ResourceLocation, inputValues: InputValueList): ServerboundCustomClickActionPacket {
        inputValues.list.forEach {
            val key = it.key
            val text = it.value

            DialogAPI.log("building Packet value : key=$key : text=$text")
        }
        val compoundTag = inputValues.toCompoundTag()
        return ServerboundCustomClickActionPacket(
            resourceLocation.toNMS(),
            Optional.of(compoundTag)
        )
    }
}
