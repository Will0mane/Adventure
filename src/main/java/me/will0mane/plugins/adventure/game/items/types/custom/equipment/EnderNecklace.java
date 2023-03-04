package me.will0mane.plugins.adventure.game.items.types.custom.equipment;

import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

import java.util.Arrays;

public class EnderNecklace extends AdventureItemBuilder {

    public EnderNecklace() {
        super(Material.PLAYER_HEAD);
    }

    @Override
    public void setup() {
        getItem().rename("&5Ender Necklace")
                .setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZkYTdiOTU3YjgyMTAzYjE3MTQwZTcxMTc5MTE0NjQ4YTAwMjkxYjA1NjU3YzI3ZDNmOTNkNzI2ZTg3ZTY0MCJ9fX0=")
        .setDescription(Arrays.asList(
                "&7A powerful necklace forged",
                "&7by the enders a long time ago."
        )).setKey("EQUIPMENT_ITEM", true).setKey("EQUIPMENT_PIECE", "HEAD");
    }
}
