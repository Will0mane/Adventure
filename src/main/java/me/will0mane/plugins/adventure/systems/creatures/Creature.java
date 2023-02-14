package me.will0mane.plugins.adventure.systems.creatures;

import me.will0mane.plugins.adventure.systems.creatures.brain.EntityBrain;
import me.will0mane.plugins.adventure.systems.creatures.brain.abstraction.CreatureBrain;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public abstract class Creature {

    protected final EntityBrain brain;
    protected final Location spawnLocation;

    protected Creature(Location location){
        this.brain = new CreatureBrain(this);
        this.spawnLocation = location;
    }

    public EntityBrain getBrain() {
        return brain;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public abstract Location getLocation();

    public abstract LivingEntity getBukkit();

    public abstract EntityLiving getNMS();
}
