package me.will0mane.plugins.adventure.systems.particle;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ParticleUtils {

    public static void spawnParticleYaml(String data, Location location){
        if(data.contains(";")){
            for(String split : data.split(";")){
                String particleName = split.split(",")[0];
                int amount = Integer.parseInt(split.split(",")[1]);
                if(particleName.equalsIgnoreCase("BLOCK_BREAK")){
                    String materialBreak = split.split(",")[2];
                    Objects.requireNonNull(location.getWorld()).
                            spawnParticle(Particle.BLOCK_CRACK,
                                    location,
                                    amount,
                                    Material.valueOf(materialBreak.toUpperCase()).createBlockData());
                }else {
                    spawnParticle(location, Particle.valueOf(particleName.toUpperCase()), amount);
                }
            }
        }else {
            String particleName = data.split(",")[0];
            int amount = Integer.parseInt(data.split(",")[1]);
            if(particleName.equalsIgnoreCase("BLOCK_BREAK")){
                String materialBreak = data.split(",")[2];
                Objects.requireNonNull(location.getWorld()).
                        spawnParticle(Particle.BLOCK_CRACK,
                                location,
                                amount,
                                Material.valueOf(materialBreak.toUpperCase()).createBlockData());
            }else {
                spawnParticle(location, Particle.valueOf(particleName.toUpperCase()), amount);
            }
        }
    }

    public static void spawnParticle(Location location, Particle particle, int amount) {
        Objects.requireNonNull(location.getWorld()).spawnParticle(particle, location, amount);
    }

}
