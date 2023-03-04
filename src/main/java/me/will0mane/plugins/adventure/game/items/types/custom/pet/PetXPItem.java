package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.ReclaimPetXPAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import org.bukkit.Material;

import java.util.Arrays;

public class PetXPItem extends AdventureItemBuilder {

    private final double xp;

    public PetXPItem(String xp) {
        super(Material.PLAYER_HEAD);
        this.xp = Double.parseDouble(xp);
    }

    @Override
    public void setup() {
        int rounded = (int) Math.round(xp);
        getItem().rename(AdventureRarity.RARE.getColorPrefix() + "Pet Experience &7[&b" + rounded + "&7]")
                .setDescription(Arrays.asList(
                        "&7A powerful item",
                        "&7that gives &bpet XP&7!",
                        "&7[" + rounded + "&7]"
                )).addAbility(new ReclaimPetXPAbility()).setHead(
"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTEzODM1MmY0NzQ1ZTAyYzA5MzkxNDZkYmQzNjZlNjUzNWE3ZjRlZjM5NjUzMDA5YjVjMzljMjRiOTRkNGNhNyJ9fX0="
        )
                .setKey("xpOnClaim", xp);
    }
}
