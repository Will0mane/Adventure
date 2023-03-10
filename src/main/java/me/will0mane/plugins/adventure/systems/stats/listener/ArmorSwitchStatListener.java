package me.will0mane.plugins.adventure.systems.stats.listener;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.lib.armorequipevent.ArmorEvent;
import me.will0mane.plugins.adventure.lib.armorequipevent.ArmorType;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.modes.StatData;
import me.will0mane.plugins.adventure.systems.stats.modes.StatMode;
import me.will0mane.plugins.adventure.systems.stats.types.AmountStatistic;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ArmorSwitchStatListener extends AdventureListener {

    private static final Map<UUID, Map<ArmorType, Map<String, StatData>>> modifications = new HashMap<>();

    public ArmorSwitchStatListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onArmorEvent(ArmorEvent event){
        ArmorType type = event.getType();
        UUID uuid = event.getPlayer().getUniqueId();
        if(event.getOldArmorPiece() != null){
            removeCurrent(uuid, type);
        }
        if(event.getNewArmorPiece() != null){
            computeStats(event.getNewArmorPiece(), uuid, type);
        }
    }


    private void removeCurrent(UUID uuid, ArmorType type) {
        if (modifications.containsKey(uuid)) {
            //RESET STATS
            Map<String, StatData> stats = modifications.get(uuid).get(type);
            if(stats == null) return;
            stats.forEach((s, statData) -> {
                Optional<AdventureStat<?>> optionalStat = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(s);
                if (optionalStat.isPresent()) {
                    AmountStatistic<Double> stat = (AmountStatistic<Double>) optionalStat.get();
                    stat.set(uuid, stat.get(uuid) - statData.getValue());
                }
            });
            Map<ArmorType, Map<String,StatData>> data =  modifications.containsKey(uuid) ? modifications.get(uuid) : new HashMap<>();
            data.remove(type);
            modifications.put(uuid, data);
        }
    }

    private void computeStats(ItemStack itemStack, UUID uuid, ArmorType type) {
        if (itemStack == null || itemStack.getItemMeta() == null) return;
        Optional<AdventureItem> optionalItem = AdventureItem.getItem(itemStack);
        if (optionalItem.isEmpty()) return;
        AdventureItem item = optionalItem.get();

        Map<String, Object> statsFromItem = item.getStats();
        Map<ArmorType, Map<String, StatData>> map = modifications.containsKey(uuid) ? modifications.get(uuid) : new HashMap<>();
        Map<String, StatData> dataMap = new HashMap<>();
        statsFromItem.forEach((s, o) -> {
            dataMap.put(s, (StatData) o);
        });
        map.put(type, dataMap);

        modifications.put(uuid, map);
        statsFromItem.forEach((s, statData) -> {
            StatData data = (StatData) statData;
            if(item.getStatMode(s) == StatMode.ARMOR) {
                Optional<AdventureStat<?>> optionalStat = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(s);
                if (optionalStat.isPresent()) {
                    AmountStatistic<Double> stat = (AmountStatistic<Double>) optionalStat.get();
                    stat.set(uuid, stat.get(uuid) + data.getValue());
                }
            }
        });
    }
}
