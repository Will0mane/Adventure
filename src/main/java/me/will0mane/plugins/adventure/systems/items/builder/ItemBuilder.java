package me.will0mane.plugins.adventure.systems.items.builder;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    @Getter
    private ItemStack itemStack;

    public ItemBuilder(Material material){
        this(new ItemStack(material));
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack build(){
        return itemStack;
    }

    public ItemBuilder rename(String name){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return this;
        meta.setDisplayName(ChatUtils.translate(name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return this;
        meta.setLore(ChatUtils.translateAList(lore));
        itemStack.setItemMeta(meta);
        return this;
    }

}
