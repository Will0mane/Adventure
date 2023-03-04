package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import org.bukkit.Material;

import java.util.List;

public class PetMenuItem extends AdventureItemBuilder {

    private final AdventureRarity rarity;
    private final List<String> lore;
    private final String name;
    private final String head;

    public PetMenuItem(PetType petType) {
        this(petType.getRarity(), petType);
    }

    public PetMenuItem(AdventureRarity rarity, PetType petType){
        super(Material.PLAYER_HEAD);
        this.rarity = petType.getRarity();
        this.lore = petType.getLore();
        this.name = petType.getName();
        this.head = petType.getHeadValue();
    }

    @Override
    public void setup() {
        getItem().rename(rarity.getColorPrefix() + name).setDescription(
                lore
        ).setHead(head);
    }

}
