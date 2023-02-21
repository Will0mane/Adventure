package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import me.will0mane.plugins.adventure.systems.utils.ListUtils;
import org.bukkit.Material;

import java.util.Arrays;

public class PetMenuItem extends AdventureItemBuilder {

    private final PetType petType;

    public PetMenuItem(PetType petType) {
        super(Material.PLAYER_HEAD);
        this.petType = petType;
    }

    @Override
    public void setup() {
        getItem().rename(petType.getRarity().getColorPrefix() + petType.getName()).setDescription(ListUtils.mergeLists(
                petType.getLore(),
                Arrays.asList(
                        "",
                        "&7Left click to equip",
                        "&7Right click + Shift to",
                        "&7transform per into an item"
                )
        )).setHead(petType.getHeadValue());
    }

}
