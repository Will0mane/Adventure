package me.will0mane.plugins.adventure.systems.utils;

import org.bukkit.Location;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {

    public static final Random random = new Random();

    private MathUtil(){}

    public static int higherAt(Set<Integer> integers){
        int cur = 0;
        for(Integer integer : integers){
            if(integer > cur)
                cur = integer;
        }
        return cur;
    }

    public static Location getRandomLocationInRadius(Location loc, float radius){
        double angle = random.nextDouble()*360; //Generate a random angle
        double x = loc.getX() + (random.nextDouble()*radius*Math.cos(Math.toRadians(angle))); // x
        double z = loc.getZ() + (random.nextDouble()*radius*Math.sin(Math.toRadians(angle))); // z
        double y = ThreadLocalRandom.current().nextDouble(0,1);
        return new Location(loc.getWorld(), x, loc.getY() + y, z);
    }

}
