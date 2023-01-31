package me.will0mane.plugins.adventure.game.blueprints;

import me.will0mane.plugins.adventure.game.blueprints.nodes.PlayerNode;
import me.will0mane.plugins.adventure.game.blueprints.types.PlayerRelatedBlueprintType;
import me.will0mane.plugins.adventure.systems.blueprints.Blueprint;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PlayerInventoryClearBlueprint extends Blueprint<PlayerRelatedBlueprintType> {

    private PlayerNode node;

    public PlayerInventoryClearBlueprint(UUID uuid){
        node = new PlayerNode().setExecution(player -> player.getInventory().clear()).setPlayer(uuid);
    }

    @Override
    public List<BlueprintNode> getNodes() {
        return Collections.singletonList(node);
    }

    @Override
    public void execPin() {
        node.executePin();
    }

    @Override
    public void finishPin() {
    }
}
