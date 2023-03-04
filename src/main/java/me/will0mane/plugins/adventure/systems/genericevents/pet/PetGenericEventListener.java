package me.will0mane.plugins.adventure.systems.genericevents.pet;

import me.will0mane.plugins.adventure.systems.genericevents.GenericEvent;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PetGenericEventListener extends AdventureListener {

    public PetGenericEventListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEvent(GenericEvent event){
        AdventurePet.activePets.forEach((uuid, adventurePet) -> adventurePet.inputEvent(event));
    }

}
