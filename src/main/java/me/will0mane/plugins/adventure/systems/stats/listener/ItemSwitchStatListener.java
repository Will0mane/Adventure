package me.will0mane.plugins.adventure.systems.stats.listener;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.AdventureStatManager;
import me.will0mane.plugins.adventure.systems.stats.types.AmountStatistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ItemSwitchStatListener extends AdventureListener {

    private static final Map<UUID, Map<String, Double>> modifications = new HashMap<>();

    public ItemSwitchStatListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onSwitch(PlayerSwapHandItemsEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        removeCurrent(uuid);
        computeStats(event.getOffHandItem(), uuid);
        computeStats(event.getMainHandItem(), uuid);
    }

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        removeCurrent(uuid);
        computeStats(Objects.requireNonNull(event.getPlayer().getInventory()).getItem(event.getNewSlot()), uuid);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = event.getWhoClicked().getUniqueId();
        if(event.getSlot() == player.getInventory().getHeldItemSlot()){
            if(event.getCursor() == null){
                computeStats(event.getCurrentItem(), uuid);
            }else {
                removeCurrent(uuid);
            }
        }
    }

    private void removeCurrent(UUID uuid) {
        if (modifications.containsKey(uuid)) {
            //RESET STATS
            Map<String, Double> stats = modifications.get(uuid);
            stats.forEach((s, d) -> {
                Optional<AdventureStat<?>> optionalStat = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(s);
                if (optionalStat.isPresent()) {
                    AmountStatistic<Double> stat = (AmountStatistic<Double>) optionalStat.get();
                    stat.set(uuid, stat.get(uuid) - d);
                }
            });
            modifications.remove(uuid);
        }
    }

    private void computeStats(ItemStack itemStack, UUID uuid) {
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        Optional<AdventureItem> optionalItem = AdventureItem.getItem(itemStack);
        if (optionalItem.isEmpty()) return;
        AdventureItem item = optionalItem.get();

        Map<String, Double> statsFromItem = item.getStats();
        modifications.put(uuid, statsFromItem);
        statsFromItem.forEach((s, d) -> {
            Optional<AdventureStat<?>> optionalStat = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(s);
            if (optionalStat.isPresent()) {
                AmountStatistic<Double> stat = (AmountStatistic<Double>) optionalStat.get();
                stat.set(uuid, stat.get(uuid) + d);
            }
        });
    }
}
