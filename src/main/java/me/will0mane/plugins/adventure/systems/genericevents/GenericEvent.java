package me.will0mane.plugins.adventure.systems.genericevents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GenericEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    final @NotNull Event event;

    public GenericEvent(@NotNull Event event) {
        this.event = event;
    }

    public @NotNull Event getBukkitEvent() {
        return event;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
