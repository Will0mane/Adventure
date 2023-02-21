package me.will0mane.plugins.adventure.systems.pets.type;

import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import org.bukkit.Particle;

import java.util.Arrays;
import java.util.List;

public enum PetType {

    TIGER("Tiger", AdventureRarity.COMMON, Particle.CRIT,
            Arrays.asList(
                    "&7A combat pet:",
                    "&7Deal &cdamage &7to gain &bXP"
            ),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM4MTM3ZWEzMTI4NmJiMGEyNGI4YTZkYjkxZmMwMWVlMGJiYWQ4NTFkNWUxOGFmMGViZTI5YTk3ZTcifX19"),
    ;

    private final String headValue;
    private final String name;
    private final AdventureRarity rarity;
    private final Particle particle;
    private final List<String> lore;

    PetType(String name, AdventureRarity rarity, Particle particle, List<String> lore, String headValue) {
        this.name = name;
        this.rarity = rarity;
        this.particle = particle;
        this.lore = lore;
        this.headValue = headValue;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getHeadValue() {
        return headValue;
    }

    public AdventureRarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public Particle getParticle() {
        return particle;
    }
}
