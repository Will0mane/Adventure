package me.will0mane.plugins.adventure.systems.hologram;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.hologram.click.HologramClickManager;
import me.will0mane.plugins.adventure.systems.hologram.line.HologramLine;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.*;

public class Hologram {

    public static final List<Hologram> holograms = new ArrayList<>();

    @Getter
    private final Map<Integer, HologramLine> lines = new HashMap<>();
    @Getter
    private Location location;
    @Getter
    private final HologramClickManager clickManager;

    public Hologram(Location location, String... line){
        this.location = location;
        this.clickManager = new HologramClickManager(this);
        int i = 0;
        for(String string : line){
            lines.put(i, spawnLine(string, location));
            i++;
        }
        holograms.add(this);
        updateLines();
    }

    public void addLine(String string){
        int last = getLastIndex();
        lines.put(last + 1, spawnLine(string, location));
        updateLines();
    }

    public void setLocation(Location location){
        this.location = location;
        updateLines();
    }

    private int getLastIndex() {
        int i = 0;
        for(Map.Entry<Integer, HologramLine> lineEntry : lines.entrySet()){
            if(lineEntry.getKey() > i){
                i = lineEntry.getKey();
            }
        }
        return i;
    }

    public void removeLine(int index){
        ArmorStand stand = lines.get(index).armorStand();
        stand.remove();
        lines.remove(index);
        updateLines();
    }

    public void setLine(int index, String string){
        removeLine(index);
        lines.put(index, spawnLine(string, location));
        updateLines();
    }

    public void updateLines() {
        double offset = 0;
        double totalOffset = 0.5 * lines.size();
        Location startUp = location.clone().add(0,totalOffset,0);
        for(Map.Entry<Integer, HologramLine> line : lines.entrySet()){
            Location tpLocation = startUp.clone().subtract(0,offset,0);
            line.getValue().armorStand().teleport(tpLocation);
            offset += 0.5;
        }
        clickManager.updateLines();
    }

    private HologramLine spawnLine(String string, Location location) {
        ArmorStand armorStand = Objects.requireNonNull(location.getWorld()).spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(ChatUtils.translate(string));
        return new HologramLine(string, armorStand);
    }

    public HologramLine getLine(int key) {
        if(!lines.containsKey(key)) return null;
        return lines.get(key);
    }

    public void removeHologram() {
        lines.forEach((integer, hologramLine) -> {
            hologramLine.armorStand().remove();
        });
    }
}
