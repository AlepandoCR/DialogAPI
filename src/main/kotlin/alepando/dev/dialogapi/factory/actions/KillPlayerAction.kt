package alepando.dev.dialogapi.factory.actions

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.Plugin

/**
 * A custom dialog action that damages a player and optionally listens for their death.
 *
 * This action is typically used to demonstrate server-side dialog interactions
 * that have direct consequences on gameplay.
 *
 * When triggered, the player receives 5 points of damage.
 * If a dynamic listener is active, it will listen for the player's [PlayerDeathEvent]
 * and respond accordingly.
 */
object KillPlayerAction : CustomAction() {

    /**
     * Executes the custom logic for this dialog action.
     *
     * - Damages the specified player by 5 health points.
     * - Starts the [dynamicListener] (if any) and schedules it to stop after 20 ticks.
     *
     * @param player The [Player] involved in the dialog action.
     * @param plugin The plugin instance, used for scheduling.
     */
    override fun task(player: Player, plugin: Plugin) {
        dynamicListener?.start()
        dynamicListener?.stopListenerAfter(20L)
        player.damage(5.0)
    }

    /**
     * Provides a listener for player death events tied to this action.
     *
     * When the specified [dialogPlayer] dies, a message is sent to them.
     * This listener is intended to be dynamically registered for a limited time.
     *
     * @param dialogPlayer The [Player] associated with the dialog session.
     * @return A [Listener] that reacts to the player's death.
     */
    override fun listener(dialogPlayer: Player): Listener {
        return object : Listener {

            /**
             * Called when a player dies.
             *
             * If the dead player is the one linked to the dialog,
             * they receive a custom message.
             *
             * @param event The [PlayerDeathEvent] fired when a player dies.
             */
            @EventHandler
            fun onPlayerDeath(event: PlayerDeathEvent) {
                if (event.player == dialogPlayer) {
                    dialogPlayer.sendMessage("you died during your dialog")
                }
            }
        }
    }
}
