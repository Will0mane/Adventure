package me.will0mane.plugins.adventure.systems.blueprints.abs;

import me.will0mane.plugins.adventure.systems.blueprints.Blueprint;
import me.will0mane.plugins.adventure.systems.blueprints.abs.nodes.loregen.LoreGenerationNode;
import me.will0mane.plugins.adventure.systems.blueprints.abs.types.AdventureItemRelatedBlueprint;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;

import java.util.Collections;
import java.util.List;

public class BlueprintCAdventureItemLoreGeneration extends Blueprint<AdventureItemRelatedBlueprint> {

    private final LoreGenerationNode node;

    public BlueprintCAdventureItemLoreGeneration(AdventureItem item){
        this.node = new LoreGenerationNode(item);
    }

    @Override
    public List<BlueprintNode> getNodes() {
        return Collections.singletonList(node);
    }

    @Override
    public void execPin() {
        node.executePin();
    }

    public List<String> run(){
        return node.executePin();
    }

    @Override
    public void finishPin() {
    }
}
