package me.will0mane.plugins.adventure.game.items.types.custom.armors;

import me.will0mane.plugins.adventure.lib.armorequipevent.ArmorType;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.stats.modes.StatMode;
import org.bukkit.Color;

public class StrongArmor extends AdventureItemBuilder {

    private final ArmorType type;

    public StrongArmor(String type) {
        super(ArmorType.valueOf(type).getMaterialColorable());
        this.type = ArmorType.valueOf(type);
    }

    @Override
    public void setup() {
        getItem().rename("&7Strong " + type.getName()).setStatistic("strength", 10, StatMode.ARMOR);
        if(type == ArmorType.HELMET){
            getItem().setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ExNDMyNmFlYWM5N2Y5NjE4OWJiOTk2OWU3Yjk1NWVjNjUwZDM3YjdjOTc5MDM1NmZmMDlmMzIwMTBmMWFlMCJ9fX0=");
        }else {
            getItem().color(Color.fromRGB(156,156,156));
        }
    }
}
