package alepando.dev.versionSupplier

import org.bukkit.Bukkit

object ViaVersionChecker {
    val isInstalled: Boolean by lazy {
        Bukkit.getPluginManager().isPluginEnabled("ViaVersion")
    }
}
