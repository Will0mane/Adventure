package me.will0mane.plugins.adventure.game.items.types.custom;

import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.YellowStoneAbility;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import org.bukkit.Material;

import java.util.Arrays;

public class YellowStone extends AdventureItemBuilder {

    private static final String UNCHARGED_HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzkzM2VlMjM2NTg4OGZkOTZhZDg4MzNlMTNjMjI0NzVmMGVmNjEzYWRkNzU0MWI3MmMyYWMzYzVmZTQyOGUyMSJ9fX0=";

    public YellowStone() {
        super(Material.PLAYER_HEAD);
    }

    @Override
    public void setup() {
        getItem().rename("&eYellowstone").addAbility(new YellowStoneAbility()).setDescription(Arrays.asList(
                "&7A powerful substance",
                "&7that functions like",
                "&credstone &7but is not really",
                "&credstone&7!")).setHead(UNCHARGED_HEAD);
    }
}
