package me.will0mane.plugins.adventure.game.items.types.custom;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.PowerfulArrowTrigger;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.stats.modes.StatMode;
import org.bukkit.Material;

public class PowerfulBow extends AdventureItemBuilder {

    public PowerfulBow() {
        super(Material.BOW);
    }

    @Override
    public void setup() {
        getItem().rename("&cPowerful Bow").addAbility(new PowerfulArrowTrigger()).setStatistic("strength", 5, StatMode.HOLD);
    }
}
