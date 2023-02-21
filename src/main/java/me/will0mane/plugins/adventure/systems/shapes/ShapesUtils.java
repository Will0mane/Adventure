package me.will0mane.plugins.adventure.systems.shapes;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShapesUtils {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private ShapesUtils(){}

    public static List<Block> getSphere(Location location, int radius, boolean empty) {
        List<Block> blocks = new ArrayList<>();

        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y));
                    if (distance < radius * radius && (!empty && distance < (radius - 1) * (radius - 1))) {
                        blocks.add(new Location(location.getWorld(), x, y, z).getBlock());
                    }
                }
            }
        }
        return blocks;
    }

    public static Location getRandomOffsettedLocation(Location location, double i, double i1, double i2) {
        return location.clone().add(
                random.nextDouble(-i,i),
                random.nextDouble(-i1, i1),
                random.nextDouble(-i2, i2)
        );
    }
}
