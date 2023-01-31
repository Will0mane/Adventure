package me.will0mane.plugins.adventure.systems.items.builder;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AdventureItemBuilder {

    private AdventureItem item;

    public AdventureItemBuilder(Material material){
        this.item = new AdventureItem(material);
    }

    public AdventureItem getItem() {
        return item;
    }

    public abstract void setup();
    public ItemStack getItemStack(){
        return item.buildItem();
    }
}
