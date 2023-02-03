package me.will0mane.plugins.adventure.systems.listeners.abs;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AdventureListener implements Listener {

    @Getter
    private final JavaPlugin javaPlugin;

    protected AdventureListener(JavaPlugin plugin){
        this.javaPlugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, getJavaPlugin());
    }

}
