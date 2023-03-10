package me.will0mane.plugins.adventure.game.items.types.custom.backpacks;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.backpack.BackpackAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Backpack extends AdventureItemBuilder {

    private final BackpackType type;

    public Backpack(String args) {
        super(Material.PLAYER_HEAD);
        this.type = BackpackType.valueOf(args);
    }


    @Override
    public void setup() {
        getItem().rename(type.getColorPrefix() + type.getName() + " Backpack").setDescription(Arrays.asList(
                "&7A portable storage!"
        )).addAbility(new BackpackAbility()).setHead(type.getHeadValue())
                .setKey("backpackInventory", new ItemStack[]{})
                .setKey("backpackSize", type.getSize())
                .setKey("backpack", true)
                .setKey("armorMovable", false);
    }
}
