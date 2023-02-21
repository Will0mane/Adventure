package me.will0mane.plugins.adventure.systems.listeners.manager;

import me.will0mane.plugins.adventure.game.listeners.DamageHologramListener;
import me.will0mane.plugins.adventure.systems.listeners.DefaultListener;
import me.will0mane.plugins.adventure.systems.listeners.InteractListener;
import me.will0mane.plugins.adventure.systems.listeners.JoinListener;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdventureListenerManager {

    private static final Map<String, AdventureListener> listeners = new HashMap<>();

    public void registerListener(String id, AdventureListener listener){
        listeners.put(id, listener);
    }

    public Optional<AdventureListener> get(String id){
        if(!listeners.containsKey(id)) return Optional.empty();
        return Optional.of(listeners.get(id));
    }

    public void registerDefaults(JavaPlugin javaPlugin){
        registerListener("join", new JoinListener(javaPlugin));
        registerListener("interact", new InteractListener(javaPlugin));
        registerListener("default", new DefaultListener(javaPlugin));
        registerListener("dmghologram", new DamageHologramListener(javaPlugin));
    }


}
