package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.listeners.events.DamageHologramEvent;
import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;
import me.will0mane.plugins.adventure.systems.stats.types.AmountStatistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

public class DamageListener extends AdventureListener {
    public DamageListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();

        if(!(damager instanceof Player player)) return;
        UUID uuid = player.getUniqueId();

        //Make sure we don't get stats of a player that isn't connected rightfully.
        Optional<PlayerSession> optionalSession = Adventure.getRegistry().getSessionsHandler().get(uuid);
        if(optionalSession.isEmpty()) return;

        double strength = getStatistic(uuid, "strength");
        double critDamage = getStatistic(uuid, "critDamage");
        double baseMultiplier = 1;
        double postMultiplier = 1;

        double finalDamage = (5 + event.getDamage()) * (1 + (strength / 100)) * (1 + (critDamage/100)) * (1 + (baseMultiplier / 100)) * (1 + (postMultiplier / 100));
        event.setDamage(finalDamage);
        DamageHologramEvent hologramEvent = new DamageHologramEvent(event.getEntity(), event.getCause(), finalDamage);
        Bukkit.getPluginManager().callEvent(hologramEvent);
    }

    private double getStatistic(UUID uuid, String id) {
        AmountStatistic<Double> statistic = (AmountStatistic<Double>) Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(id).get();
        return statistic.get(uuid);
    }
}
