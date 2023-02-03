package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultListener extends AdventureListener {

    public DefaultListener(JavaPlugin plugin){
        super(plugin);
    }
}
