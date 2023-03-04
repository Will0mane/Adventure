package me.will0mane.plugins.adventure.systems.items.abilities.data;

import me.will0mane.plugins.adventure.systems.items.abilities.AbilityData;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

public class PetInteractionAbility extends AbilityData {

    protected final String id;
    protected final PlayerInteractAtEntityEvent event;

    public PetInteractionAbility(PlayerInteractAtEntityEvent event){
        this.id = UUID.randomUUID().toString();
        this.event = event;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String activationMethodName() {
        return "ON INTERACT AT PET";
    }

    public PlayerInteractAtEntityEvent getEvent() {
        return event;
    }
}
