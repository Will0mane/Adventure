package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom;

import me.will0mane.plugins.adventure.game.items.abilities.data.BlockPlaceData;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import org.bukkit.Location;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YellowStoneAbility extends ItemAbility<BlockPlaceData> {

    public static final Map<Vector, Double> powerMap = new HashMap<>();

    public YellowStoneAbility() {
        super("yellow_stone");
    }

    @Override
    public void trigger(BlockPlaceData data) {
        BlockPlaceEvent event = data.getEvent();

        powerMap.put(event.getBlockPlaced().getLocation().toVector(), 0D);
    }

    @Override
    public String getName() {
        return "YELLOW STONE";
    }

    @Override
    public List<String> getDescription() {
        return Collections.emptyList();
    }

    @Override
    public String activationMethodName() {
        return "ON PLACE";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.YELLOW_STONE;
    }
}
