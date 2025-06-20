package alepando.dev.dialogapi.util

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class DynamicListener(
    private val plugin: Plugin
) {
    private var activeListener = false
    private var listener: Listener? = null

    private fun unRegisterListener(){
        listener?.let {
            HandlerList.unregisterAll(listener!!)
            activeListener = false
        }
    }

    private fun registerListener(){
        listener?.let {
            plugin.server.pluginManager.registerEvents(listener!!,plugin)
            activeListener = true
        }
    }

    private fun checkIfRegistered(): Boolean {
        for (registeredListener in HandlerList.getRegisteredListeners(plugin)) {
            if (registeredListener.listener == listener) return true
        }
        return false
    }

    fun deleteListener(){
        listener?.let { unRegisterListener() }
        listener = null
    }

    fun stop(){
        unRegisterListener()
    }

    fun start(){
        if(checkIfRegistered()) return
        registerListener()
    }

    fun setListener(listener: Listener){
        this.listener?.let { unRegisterListener() }

        this.listener = listener
    }

    fun isPresent(): Boolean{
        listener ?: return false
        return true
    }

    fun getListener(): Listener?{
        return listener
    }

    fun isActive(): Boolean{
        return activeListener
    }
}