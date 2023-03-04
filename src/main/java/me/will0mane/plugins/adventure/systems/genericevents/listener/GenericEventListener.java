package me.will0mane.plugins.adventure.systems.genericevents.listener;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.genericevents.GenericEvent;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GenericEventListener extends AdventureListener {

    public GenericEventListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEvent(EntityDamageEvent event){
        GenericEvent genericEvent = new GenericEvent(event);
        Bukkit.getPluginManager().callEvent(genericEvent);
    }

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event){
        GenericEvent genericEvent = new GenericEvent(event);
        Bukkit.getPluginManager().callEvent(genericEvent);
    }

    @EventHandler
    public void onEvent(BlockPlaceEvent event){
        GenericEvent genericEvent = new GenericEvent(event);
        Bukkit.getPluginManager().callEvent(genericEvent);
    }

    @EventHandler
    public void onEvent(BlockBreakEvent event){
        GenericEvent genericEvent = new GenericEvent(event);
        Bukkit.getPluginManager().callEvent(genericEvent);
    }

    @EventHandler
    public void onEvent(PlayerMoveEvent event){
        GenericEvent genericEvent = new GenericEvent(event);
        Bukkit.getPluginManager().callEvent(genericEvent);
    }
}
