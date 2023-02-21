package me.will0mane.plugins.adventure.systems.items.blueprints;

import me.will0mane.plugins.adventure.systems.blueprints.Blueprint;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.blueprints.nodes.HeadDataNode;
import me.will0mane.plugins.adventure.systems.items.blueprints.types.AdventureItemRelatedBlueprint;

import java.util.Collections;
import java.util.List;

public class BlueprintCAdventureItemHeadData extends Blueprint<AdventureItemRelatedBlueprint> {

    private final HeadDataNode node;

    public BlueprintCAdventureItemHeadData(AdventureItem item, String data){
        this.node = new HeadDataNode(item, data);
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
