package me.will0mane.plugins.adventure.systems.hologram.click;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.hologram.Hologram;
import me.will0mane.plugins.adventure.systems.hologram.line.HologramLine;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.function.Consumer;

public class HologramClickManager {

    @Getter
    private final Hologram hologram;
    private final List<HologramClickEvent> events;
    private final Map<Integer, Slime> slimeMap;

    public HologramClickManager(Hologram hologram){
        this.hologram = hologram;
        this.events = new ArrayList<>();
        this.slimeMap = new HashMap<>();
    }

    public void addTotalListener(Consumer<Player> click){
        for(Map.Entry<Integer, HologramLine> lineEntry: hologram.getLines().entrySet()){
            events.add(new HologramClickEvent(lineEntry.getKey(), this, click));
            slimeMap.put(lineEntry.getKey(), spawnSlime(lineEntry.getValue().armorStand().getLocation()));
        }
    }

    private Slime spawnSlime(Location location) {
        Slime slime = Objects.requireNonNull(location.getWorld()).spawn(location, Slime.class);
        slime.setAI(false);
        slime.setAware(false);
        slime.setCollidable(false);
        slime.setInvulnerable(true);
        slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5000, 0));
        return slime;
    }

    public void updateLines() {
        if(events.isEmpty()) return;
        for(Map.Entry<Integer, Slime> slimeEntry : slimeMap.entrySet()){
            HologramLine line = hologram.getLine(slimeEntry.getKey());
            if(line == null) continue;
            ArmorStand armorStand = line.armorStand();
            Location location = armorStand.getLocation().clone().add(0,2,0);
            slimeEntry.getValue().teleport(location);
        }
    }

    public Slime getClickEntity(int line) {
        if(slimeMap.containsKey(line)) return slimeMap.get(line);
        return null;
    }
}
