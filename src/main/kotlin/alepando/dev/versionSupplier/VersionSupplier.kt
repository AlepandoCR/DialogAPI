package alepando.dev.versionSupplier

import alepando.dev.versionSupplier.packet.ClientVersionSniffer
import org.bukkit.entity.Player

object VersionSupplier {
    fun Player.getVersion(): Int{
        return ClientVersionSniffer.getProtocolVersionForUUID(this.uniqueId) ?: 0
    }
}