package me.will0mane.plugins.adventure.systems.npcs;

import org.bukkit.Location;

public interface NPC {

    public NPC createNPC(Location location);
    public NPC setup();

}
