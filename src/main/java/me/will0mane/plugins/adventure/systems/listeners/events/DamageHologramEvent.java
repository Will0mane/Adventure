package me.will0mane.plugins.adventure.systems.listeners.events;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageHologramEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final Entity entity;
    @Getter
    private final EntityDamageEvent.DamageCause cause;
    @Getter
    private final double finalDamage;

    public DamageHologramEvent(Entity entity, EntityDamageEvent.DamageCause cause, double finalDamage){
        this.entity = entity;
        this.cause = cause;
        this.finalDamage = finalDamage;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }
}
