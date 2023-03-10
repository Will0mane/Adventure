package me.will0mane.plugins.adventure.systems.items.blueprints.nodes;

import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Collections;
import java.util.List;

public class ColorDataNode extends BlueprintNode {

    private final AdventureItem item;
    private final Color data;

    public ColorDataNode(AdventureItem item, Color data){
        this.item = item;
        this.data = data;
    }

    @Override
    public List<?> inputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<?> outputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<AdventureItem> executePin(Object... objects) {
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) item.getOriginal().getItemMeta();
        if(itemMeta == null) return Collections.singletonList(item);

        itemMeta.setColor(data);
        item.getOriginal().setItemMeta(itemMeta);
        return Collections.singletonList(item);
    }
}
