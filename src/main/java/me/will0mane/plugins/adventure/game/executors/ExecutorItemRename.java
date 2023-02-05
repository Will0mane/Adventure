package me.will0mane.plugins.adventure.game.executors;

import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.executors.BiExecutor;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.inventory.meta.ItemMeta;

public class ExecutorItemRename extends BiExecutor<AdventureItem, String, AdventureItem> {

    @Override
    public AdventureItem apply(AdventureItem item, String string) {
        ItemMeta meta = item.getOriginal().getItemMeta();
        if(meta == null) return item;
        meta.setDisplayName(ChatUtils.translate(string));
        item.applyMeta(meta);
        return item;
    }
}
