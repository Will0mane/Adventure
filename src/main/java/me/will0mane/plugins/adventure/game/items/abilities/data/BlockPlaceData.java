package me.will0mane.plugins.adventure.game.items.abilities.data;

import me.will0mane.plugins.adventure.systems.items.abilities.AbilityData;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class BlockPlaceData extends AbilityData {

    protected final String id;
    protected final BlockPlaceEvent event;

    public BlockPlaceData(BlockPlaceEvent event){
        this.id = UUID.randomUUID().toString();
        this.event = event;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String activationMethodName() {
        return "ON PLACE";
    }

    public BlockPlaceEvent getEvent() {
        return event;
    }
}
