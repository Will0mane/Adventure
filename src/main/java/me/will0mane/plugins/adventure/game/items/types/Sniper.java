package me.will0mane.plugins.adventure.game.items.types;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.AimAbility;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.SniperHitAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

public class Sniper extends AdventureItemBuilder {
    public Sniper() {
        super(Material.DIAMOND_HORSE_ARMOR);
    }

    @Override
    public void setup() {
        super.getItem().rename("&7Cecchino").addAbility(new SniperHitAbility()).addAbility(new AimAbility());
    }
}
