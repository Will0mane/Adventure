package me.will0mane.plugins.adventure.systems.gui.item;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.gui.events.ClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Item {

    @Getter
    private final ItemStack itemStack;
    @Getter
    private final Consumer<ClickEvent> eventConsumer;

    private Item(ItemStack itemStack, Consumer<ClickEvent> e){
        this.itemStack = itemStack;
        this.eventConsumer = e;
    }

    public static Item of(ItemStack item, Consumer<ClickEvent> e){
        return new Item(item, e);
    }
    public static Item empty(ItemStack item){
        return of(item, clickEvent-> {});
    }

}
