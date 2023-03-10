package me.will0mane.plugins.adventure.lib.armorequipevent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;

public class DispenserArmorListener implements Listener {

    @EventHandler
    public void onArmorDispense(BlockDispenseArmorEvent event) {
        ArmorType type = ArmorType.matchType(event.getItem());
        if (type != null && event.getTargetEntity() instanceof Player p) {
            ArmorEvent armorEquipEvent = new ArmorEvent(p, ArmorEvent.EquipMethod.DISPENSER, type, null, event.getItem());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}