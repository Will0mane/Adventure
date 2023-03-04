package me.will0mane.plugins.adventure.game.items.types.custom.equipment;

import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

import java.util.Arrays;

public class MagmaNecklace extends AdventureItemBuilder {

    public MagmaNecklace() {
        super(Material.PLAYER_HEAD);
    }

    @Override
    public void setup() {
        getItem().rename("&cMagma Necklace")
                .setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzNlMjIzN2JlZjRmNGY3ZTk1YmI2ZWEyOWI0ZmY2ZjVkZTg2YzBkNWYyZDhjOTljMTY0YTBhOTg0OGU3ODcwNCJ9fX0=")
                .setDescription(Arrays.asList(
                        "&7A powerful necklace forged",
                        "&7by the magma."
                )).setKey("EQUIPMENT_ITEM", true).setKey("EQUIPMENT_PIECE", "HEAD");
    }
}
