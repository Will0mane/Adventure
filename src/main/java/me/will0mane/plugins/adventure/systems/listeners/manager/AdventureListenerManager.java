package me.will0mane.plugins.adventure.systems.listeners.manager;

import me.will0mane.plugins.adventure.game.listeners.DamageHologramListener;
import me.will0mane.plugins.adventure.systems.genericevents.listener.GenericEventListener;
import me.will0mane.plugins.adventure.systems.genericevents.pet.PetGenericEventListener;
import me.will0mane.plugins.adventure.systems.listeners.*;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.stats.listener.ArmorSwitchStatListener;
import me.will0mane.plugins.adventure.systems.stats.listener.ItemSwitchStatListener;
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
        registerListener("generics", new GenericEventListener(javaPlugin));
        registerListener("petgeneric", new PetGenericEventListener(javaPlugin));
        registerListener("petinteract", new PetInteractListener(javaPlugin));
        registerListener("join", new JoinListener(javaPlugin));
        registerListener("interact", new InteractListener(javaPlugin));
        registerListener("default", new DefaultListener(javaPlugin));
        registerListener("dmg", new DamageListener(javaPlugin));
        registerListener("dmghologram", new DamageHologramListener(javaPlugin));
        registerListener("itemswitchstat", new ItemSwitchStatListener(javaPlugin));
        registerListener("armorswitchstat", new ArmorSwitchStatListener(javaPlugin));
    }


}
