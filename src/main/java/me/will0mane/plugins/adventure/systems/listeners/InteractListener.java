package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.data.BlockActionAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.player.AdventurePlayer;
import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

public class InteractListener extends AdventureListener {

    public InteractListener(JavaPlugin plugin){
        super(plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.hasItem()) return;
        AdventurePlayer player = AdventurePlayer.of(event.getPlayer());
        Optional<PlayerSession> optionalSession = player.getSession();

        if(optionalSession.isEmpty()) return;

        inputAbility(event.getItem(), InteractAbility.class, event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        inputAbility(Objects.requireNonNull(event.getPlayer().getEquipment()).getItemInMainHand(),
                BlockActionAbility.class, event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        inputAbility(Objects.requireNonNull(event.getPlayer().getEquipment()).getItemInMainHand(),
                BlockActionAbility.class, event);
    }

    private void inputAbility(ItemStack itemStack, Class<?> clazz, Event event){
        if(itemStack.getItemMeta() == null) return;
        Optional<AdventureItem> item = AdventureItem.getItem(itemStack);
        if(item.isEmpty()) return;
        AdventureItem adventureItem = item.get();
        adventureItem.inputAbility(clazz, event);
    }

}
