package me.will0mane.plugins.adventure.game.listeners;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.hologram.Hologram;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.listeners.events.DamageHologramEvent;
import me.will0mane.plugins.adventure.systems.shapes.ShapesUtils;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import me.will0mane.plugins.adventure.systems.workers.tasks.ConsumerTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class DamageHologramListener extends AdventureListener {

    private static final Worker<?> worker = new Worker<>(Adventure.getInstance());

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public DamageHologramListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onDamage(DamageHologramEvent event) {

        damage(event.getEntity(),
                event.getCause() ==
                        EntityDamageEvent.DamageCause.ENTITY_ATTACK ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                        ? ChatColor.RED : ChatColor.GRAY,
                event.getFinalDamage() + 1);
    }

    public void damage(Entity entity, ChatColor color, double damage) {
        Hologram hologram = new Hologram(ShapesUtils.getRandomOffsettedLocation(entity.getLocation(), 1, 1, 1).add(0,1.5,0),
                color + "" + format((long) damage));
        worker.later(new ConsumerTask(worker, w -> hologram.removeHologram()), 10);
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10f);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

}
