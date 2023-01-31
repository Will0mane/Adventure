package me.will0mane.plugins.adventure.game.items.types;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.VeinMinerAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

public class VeinMinerPickaxe extends AdventureItemBuilder {
    public VeinMinerPickaxe() {
        super(Material.DIAMOND_PICKAXE);
    }

    @Override
    public void setup() {
        super.getItem().rename("&7Vein Pickaxe").addAbility(new VeinMinerAbility());
    }
}
