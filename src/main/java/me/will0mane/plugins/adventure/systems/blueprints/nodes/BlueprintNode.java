package me.will0mane.plugins.adventure.systems.blueprints.nodes;

import java.util.List;
import java.util.function.Consumer;

public abstract class BlueprintNode {

    private Consumer<BlueprintNode> endOfCompute = null;

    public abstract List<?> inputVars();
    public abstract List<?> outputVars();
    public abstract List<?> executePin(Object... objects);

    public Consumer<BlueprintNode> getEndOfCompute() {
        return endOfCompute;
    }

    public void setEndOfCompute(Consumer<BlueprintNode> endOfCompute) {
        this.endOfCompute = endOfCompute;
    }
}
