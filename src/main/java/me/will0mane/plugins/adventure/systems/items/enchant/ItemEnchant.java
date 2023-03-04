package me.will0mane.plugins.adventure.systems.items.enchant;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.event.Event;

import java.util.List;
import java.util.function.BiConsumer;

public class ItemEnchant {

    private final String id;
    private final int level;
    private final List<Class<Event>> listenedEvents;
    private final BiConsumer<AdventureItem, Event> eventConsumer;

    public ItemEnchant(String id, int level, List<Class<Event>> listenedEvents, BiConsumer<AdventureItem, Event> eventConsumer){
        this.id = id;
        this.level = level;
        this.listenedEvents = listenedEvents;
        this.eventConsumer = eventConsumer;
    }

    public String getId() {
        return id;
    }

    public BiConsumer<AdventureItem, Event> getEventConsumer() {
        return eventConsumer;
    }

    public List<Class<Event>> getListenedEvents() {
        return listenedEvents;
    }

    @Override
    public String toString(){
        return id + ":" + level;
    }
}
