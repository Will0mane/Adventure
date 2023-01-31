package me.will0mane.plugins.adventure.game.projectiles.hitscan.laser;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RayTraceEntities {

    public static Collection<Entity> rayTrace(Location startLoc, Vector direction, double maxDistance, double raySize){
        Vector startPos = startLoc.toVector();
        Vector dir = direction.clone().normalize().multiply(maxDistance);
        BoundingBox boundingBox = BoundingBox.of(startPos, startPos).expandDirectional(dir).expand(0);
        return Objects.requireNonNull(startLoc.getWorld()).getNearbyEntities(boundingBox);
    }

    public static List<Entity> rayTraceEntities(Location startLoc, Vector direction, double maxDistance, double raySize, double step){
        List<Entity> entityCollection = new ArrayList<>();
        for(double i = 0; i < maxDistance; i += step){
            Location neuLoc = startLoc.clone().add(direction.multiply(i));
            BoundingBox boundingBox = BoundingBox.of(neuLoc, neuLoc).expand(raySize);
            entityCollection.addAll(Objects.requireNonNull(startLoc.getWorld()).getNearbyEntities(boundingBox));
        }
        return entityCollection;
    }

    public static List<Entity> rayTraceEntitiesNew(Location startLoc, Vector direction, double maxDistance, double raySize, double step){
        List<Entity> entityCollection = new ArrayList<>();
        RayTrace trace = new RayTrace(startLoc.toVector(), startLoc.getDirection());
        for(Vector v : trace.traverse(maxDistance, step)){
            Location location = v.toLocation(Objects.requireNonNull(startLoc.getWorld()));
            entityCollection.addAll(Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, raySize, raySize, raySize));
        }
        return entityCollection;
    }

    public static List<Block> rayTraceBlocks(Location startLoc, Vector direction, double maxDistance){
        Vector startPos = startLoc.toVector();
        Vector dir = direction.clone().normalize().multiply(maxDistance);
        BoundingBox boundingBox = BoundingBox.of(startPos, startPos).expandDirectional(dir).expand(0);
        Vector max = boundingBox.getMax();
        Vector min = boundingBox.getMin();
        List<Block> list = new ArrayList<>();
        for (int i = min.getBlockX(); i <= max.getBlockX();i++) {
            for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k <= max.getBlockZ();k++) {
                    list.add(Objects.requireNonNull(startLoc.getWorld()).getBlockAt(i,j,k)); //or however you are wanting to store the locations
                }
            }
        }
        return list;
    }

}
