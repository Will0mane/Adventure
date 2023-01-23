package me.will0mane.plugins.adventure.systems.creatures.brain.memories;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMemory {

    protected final List<EntityMemories> entityMemories;

    public EntityMemory(){
        this.entityMemories = new ArrayList<>();
    }

    public List<EntityMemories> getEntityMemories() {
        return entityMemories;
    }
}
