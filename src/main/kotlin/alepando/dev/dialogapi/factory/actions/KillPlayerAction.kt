package alepando.dev.dialogapi.factory.actions

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

object KillPlayerAction: CustomAction() {
    override fun task(player: Player,plugin: Plugin) {
        dynamicListener?.start()
        player.damage(5.0)

        object : BukkitRunnable(){
            override fun run() {
                dynamicListener?.stop()
            }
        }.runTaskLater(plugin, 20L)
    }

    override fun listener(): Listener {
        return  object : Listener{
            @EventHandler
            fun onPlayerDeath(event: PlayerDeathEvent){
                event.player.sendMessage("holis")
            }
        }
    }
}