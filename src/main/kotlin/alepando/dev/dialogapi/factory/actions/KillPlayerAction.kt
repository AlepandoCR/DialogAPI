package alepando.dev.dialogapi.factory.actions

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

/**
 * A custom action that damages a player and listens for their death event.
 */
object KillPlayerAction: CustomAction() {
    /**
     * Damages the player and starts listening for death events.
     * The listener is stopped after 20 ticks (1 second).
     *
     * @param player The player to damage.
     * @param plugin The plugin instance.
     */
    override fun task(player: Player,plugin: Plugin) {
        dynamicListener?.start()
        player.damage(5.0)

        object : BukkitRunnable(){
            override fun run() {
                dynamicListener?.stop()
            }
        }.runTaskLater(plugin, 20L)
    }

    /**
     * Provides a listener that sends a message to the player upon their death.
     * @return A [Listener] instance.
     */
    override fun listener(): Listener {
        return  object : Listener{
            /**
             * Handles the [PlayerDeathEvent].
             * @param event The [PlayerDeathEvent].
             */
            @EventHandler
            fun onPlayerDeath(event: PlayerDeathEvent){
                event.player.sendMessage("holis")
            }
        }
    }
}