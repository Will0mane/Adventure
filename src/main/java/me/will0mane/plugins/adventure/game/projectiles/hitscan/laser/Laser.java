package me.will0mane.plugins.adventure.game.projectiles.hitscan.laser;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class Laser {

    private final Location startLoc;
    private final Location endLoc;
    private final Vector vector;
    private final float maxDistance;
    private final double raySize;
    private final double step;

    public Laser(Location location, Location locationEnd, Vector vector,
                 float maxDistance, double raySize, double step) {
        startLoc = location;
        endLoc = locationEnd;
        this.vector = vector;
        this.maxDistance = maxDistance;
        this.raySize = raySize;
        this.step = step;
    }


    public Location getEndLoc() {
        return endLoc;
    }

    public Location getStartLoc() {
        return startLoc;
    }

    public Vector getVector() {
        return vector;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public double getStep() {
        return step;
    }

    public void highlight(String particle, double accuracy, double blocksAway, double particleData){
        new RayTrace(getStartLoc().toVector(), getVector()).highlight(startLoc.getWorld(), blocksAway, accuracy, particle, particleData);
    }

    public Collection<Entity> rayCastEntities(){
        return RayTraceEntities.rayTraceEntitiesNew(getStartLoc(), getVector(), getMaxDistance(), 0, getStep());
    }

    public double getRaySize() {
        return raySize;
    }

    public List<Block> rayCastBlocks() {
        return RayTraceEntities.rayTraceBlocks(getStartLoc(), getVector(), getMaxDistance());
    }
}
