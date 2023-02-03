package me.will0mane.plugins.adventure.systems.gui.events;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClickEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    @Getter
    private final Item item;
    private final boolean isLeftClick;
    private final boolean isShifting;

    public ClickEvent(Item item, boolean isLeftClick, boolean isShifting){
        this.item = item;
        this.isLeftClick = isLeftClick;
        this.isShifting = isShifting;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public boolean isLeftClick() {
        return isLeftClick;
    }

    public boolean isShifting() {
        return isShifting;
    }

    public boolean isRightClick(){
        return !isLeftClick;
    }
}
