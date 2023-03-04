package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.PetRarityUpAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import org.bukkit.Material;

import java.util.Arrays;

public class PetRankupItem extends AdventureItemBuilder {

    public PetRankupItem() {
        super(Material.PLAYER_HEAD);
    }

    @Override
    public void setup() {
        getItem().rename(AdventureRarity.LEGENDARY.getColorPrefix() + "Pet Rankupper")
                .setDescription(Arrays.asList(
                        "&7A powerful item",
                        "&7that ranks up your pet!"
                )).addAbility(new PetRarityUpAbility()).setHead(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWNkYjhmNDM2NTZjMDZjNGU4NjgzZTJlNjM0MWI0NDc5ZjE1N2Y0ODA4MmZlYTRhZmYwOWIzN2NhM2M2OTk1YiJ9fX0=")
                .setKey("petAbility", true);
    }
}
