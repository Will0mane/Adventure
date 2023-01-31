package me.will0mane.plugins.adventure.systems.blueprints;

import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.blueprints.types.BlueprintType;

import java.util.List;

public abstract class Blueprint<T extends BlueprintType> {

    public abstract List<BlueprintNode> getNodes();
    public abstract void execPin();
    public abstract void finishPin();

}
