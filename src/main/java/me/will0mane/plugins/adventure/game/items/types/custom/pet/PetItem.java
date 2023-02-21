package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.ReclaimPetAbility;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import org.bukkit.Material;

public class PetItem extends AdventureItemBuilder {

    private final PetType petType;

    public PetItem(PetType petType) {
        super(Material.PLAYER_HEAD);
        this.petType = petType;
    }

    @Override
    public void setup() {
        getItem().rename(petType.getRarity().getColorPrefix() + petType.getName()).setDescription(petType.getLore()).addAbility(new ReclaimPetAbility()).setHead(petType.getHeadValue())
        .setKey("petData", DataType.STRING, petType.name() + ":0:10:0");
    }
}
