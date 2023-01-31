package me.will0mane.plugins.adventure.game.items.types;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.FastBowAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

public class FastBow extends AdventureItemBuilder {

    public FastBow() {
        super(Material.BOW);
    }

    @Override
    public void setup() {
        super.getItem().rename("&7Fast Bow").addAbility(new FastBowAbility());
    }
}
