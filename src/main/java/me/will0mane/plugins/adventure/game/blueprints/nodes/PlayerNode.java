package me.will0mane.plugins.adventure.game.blueprints.nodes;

import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class PlayerNode extends BlueprintNode {

    private UUID player;
    private Consumer<Player> accept;

    @Override
    public List<?> inputVars() {
        return Collections.emptyList();
    }

    public PlayerNode setPlayer(UUID player) {
        this.player = player;
        return this;
    }

    public PlayerNode setExecution(Consumer<Player> accept) {
        this.accept = accept;
        return this;
    }

    @Override
    public List<?> outputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<?> executePin(Object... objects) {
        accept.accept(Bukkit.getPlayer(player));
        return outputVars();
    }
}
