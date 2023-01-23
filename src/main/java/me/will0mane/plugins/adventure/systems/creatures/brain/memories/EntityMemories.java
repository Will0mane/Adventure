package me.will0mane.plugins.adventure.systems.creatures.brain.memories;

import me.will0mane.plugins.adventure.systems.creatures.Creature;

import java.util.function.Consumer;

public abstract class EntityMemories {

    public abstract Consumer<Creature> onMemoryRemember();

}
