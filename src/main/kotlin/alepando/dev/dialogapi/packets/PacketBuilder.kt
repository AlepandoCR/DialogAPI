package alepando.dev.dialogapi.packets

import alepando.dev.dialogapi.factory.data.ResourceLocation
import alepando.dev.dialogapi.packets.parser.PayloadParser.toCompoundTag
import alepando.dev.dialogapi.util.InputValueList
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket
import java.util.*

object PacketBuilder {

    fun build(resourceLocation: ResourceLocation, inputValues: InputValueList): ServerboundCustomClickActionPacket {
        val compoundTag = inputValues.toCompoundTag()
        return ServerboundCustomClickActionPacket(
            resourceLocation.toNMS(),
            Optional.of(compoundTag)
        )
    }
}
