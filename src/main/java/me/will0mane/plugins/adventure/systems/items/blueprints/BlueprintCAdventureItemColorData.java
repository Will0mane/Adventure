package me.will0mane.plugins.adventure.systems.items.blueprints;

import me.will0mane.plugins.adventure.systems.blueprints.Blueprint;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.blueprints.nodes.ColorDataNode;
import me.will0mane.plugins.adventure.systems.items.blueprints.types.AdventureItemRelatedBlueprint;
import org.bukkit.Color;

import java.util.Collections;
import java.util.List;

public class BlueprintCAdventureItemColorData extends Blueprint<AdventureItemRelatedBlueprint> {

    private final ColorDataNode node;

    public BlueprintCAdventureItemColorData(AdventureItem item, Color data){
        this.node = new ColorDataNode(item, data);
    }

    @Override
    public List<BlueprintNode> getNodes() {
        return Collections.singletonList(node);
    }

    @Override
    public void execPin() {
        node.executePin();
    }

    public List<AdventureItem> run(){
        return node.executePin();
    }

    @Override
    public void finishPin() {
    }
}