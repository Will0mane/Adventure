package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class InteractListener implements Listener {

    public InteractListener(JavaPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() == null ||
        e.getItem() == null) return;
        if(!e.hasItem()) return;
        Optional<AdventureItem> item = AdventureItem.getItem(e.getItem());
        if(item.isEmpty()) return;
        AdventureItem adventureItem = item.get();
        adventureItem.inputAbility(InteractAbility.class, e);
    }

}
