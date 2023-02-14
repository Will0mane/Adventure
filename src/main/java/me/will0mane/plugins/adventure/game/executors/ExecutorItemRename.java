package me.will0mane.plugins.adventure.game.executors;

import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.executors.BiExecutor;
import me.will0mane.plugins.adventure.systems.executors.hash.HashBiExecutor;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiFunction;

public class ExecutorItemRename extends HashBiExecutor<AdventureItem, String, AdventureItem> {

    public ExecutorItemRename() {
        super((item, string) -> {
            ItemMeta meta = item.getOriginal().getItemMeta();
            if(meta == null) return item;
            meta.setDisplayName(ChatUtils.translate(string));
            item.applyMeta(meta);
            return item;
        });
    }
}
