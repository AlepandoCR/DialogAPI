package alepando.dev.versionSupplier

import alepando.dev.dialogapi.DialogAPI
import alepando.dev.versionSupplier.packet.ClientVersionSniffer
import com.viaversion.viaversion.api.Via
import org.bukkit.entity.Player

object VersionSupplier {

    fun Player.getVersion(): Int{
        return ClientVersionSniffer.getProtocolVersionForUUID(this.uniqueId) ?: 0
    }

    fun Player.getViaVersion(): Int{
        if(!ViaVersionChecker.isInstalled) return 0
        val version = Via.getAPI().getPlayerVersion(this.uniqueId)
        DialogAPI.log("version for ${this.name} = $version")
        return version
    }
}