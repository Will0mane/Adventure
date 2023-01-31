package me.will0mane.plugins.adventure.systems.items.abilities.data;

import me.will0mane.plugins.adventure.systems.items.abilities.AbilityData;
import org.bukkit.event.block.BlockEvent;

import java.util.UUID;

public class BlockActionAbility extends AbilityData {

    protected final String id;
    protected final BlockEvent event;

    public BlockActionAbility(BlockEvent event){
        this.id = UUID.randomUUID().toString();
        this.event = event;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String activationMethodName() {
        return "ON BLOCK ACTION";
    }

    public BlockEvent getEvent() {
        return event;
    }
}
