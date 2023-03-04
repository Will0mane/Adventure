package me.will0mane.plugins.adventure.game.items.types.custom.pet;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.ReclaimPetAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import org.bukkit.Material;

import java.util.List;

public class PetItem extends AdventureItemBuilder {

    private final AdventureRarity rarity;
    private final String name;
    private final List<String> lore;
    private final String head;
    private final PetType id;
    private final double curXP;
    private final double maxXP;
    private final int level;

    public PetItem(PetType petType) {
        super(Material.PLAYER_HEAD);
        this.rarity = petType.getRarity();
        this.name = petType.getName();
        this.lore = petType.getLore();
        this.head = petType.getHeadValue();
        this.curXP = 0;
        this.maxXP = 10;
        this.level = 0;
        this.id = petType;
    }

    public PetItem(AdventurePet pet) {
        super(Material.PLAYER_HEAD);
        this.rarity = pet.getRarity();
        this.name = pet.getType().getName();
        this.lore = pet.getType().getLore();
        this.head = pet.getType().getHeadValue();
        this.id = pet.getType();
        this.curXP = pet.getCurXP();
        this.maxXP = pet.getMaxXP();
        this.level = pet.getLevel();
    }

    @Override
    public void setup() {
        getItem().rename(rarity.getColorPrefix() + name).setDescription(lore).addAbility(new ReclaimPetAbility()).setHead(head)
        .setKey("petData", id.name() + ":" + level + ":" + maxXP + ":" + curXP + ":" + rarity.name());
    }
}
