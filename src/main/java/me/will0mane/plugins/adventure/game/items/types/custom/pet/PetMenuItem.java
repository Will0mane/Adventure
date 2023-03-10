package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.rarity.PetRarity;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import org.bukkit.Material;

import java.util.List;

public class PetMenuItem extends AdventureItemBuilder {

    private final PetRarity rarity;
    private final List<String> lore;
    private final String name;
    private final String head;

    public PetMenuItem(PetType petType) {
        this(petType.getRarity(), petType);
    }

    public PetMenuItem(PetRarity rarity, PetType petType){
        super(Material.PLAYER_HEAD);
        this.rarity = rarity;
        this.lore = petType.getLore();
        this.name = petType.getName();
        this.head = petType.getHeadValue();
    }

    @Override
    public void setup() {
        getItem().rename(rarity.getColorPrefix() + name).setDescription(
                lore
        ).setHead(head).setKey("armorMovable", false);
    }

}
