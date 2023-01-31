package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.data.BlockActionAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Optional;

public class InteractListener implements Listener {

    public InteractListener(JavaPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(!e.hasItem()) return;
        inputAbility(e.getItem(), InteractAbility.class, e);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        inputAbility(Objects.requireNonNull(e.getPlayer().getEquipment()).getItemInMainHand(),
                BlockActionAbility.class, e);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        inputAbility(Objects.requireNonNull(e.getPlayer().getEquipment()).getItemInMainHand(),
                BlockActionAbility.class, e);
    }

    private void inputAbility(ItemStack itemStack, Class<?> clazz, Event event){
        if(itemStack.getItemMeta() == null) return;
        Optional<AdventureItem> item = AdventureItem.getItem(itemStack);
        if(item.isEmpty()) return;
        AdventureItem adventureItem = item.get();
        adventureItem.inputAbility(clazz, event);
    }

}
