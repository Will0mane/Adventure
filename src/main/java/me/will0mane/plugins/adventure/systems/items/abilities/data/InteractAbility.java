package me.will0mane.plugins.adventure.systems.items.abilities.data;

import me.will0mane.plugins.adventure.systems.items.abilities.AbilityData;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class InteractAbility extends AbilityData {

    protected final String id;
    protected final PlayerInteractEvent event;

    public InteractAbility(PlayerInteractEvent event){
        this.id = UUID.randomUUID().toString();
        this.event = event;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String activationMethodName() {
        return "ON INTERACT";
    }

    public PlayerInteractEvent getEvent() {
        return event;
    }
}
