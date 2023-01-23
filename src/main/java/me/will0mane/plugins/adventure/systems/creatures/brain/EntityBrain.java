package me.will0mane.plugins.adventure.systems.creatures.brain;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.controller.EntityController;
import me.will0mane.plugins.adventure.systems.creatures.brain.memories.CreatureMemory;
import me.will0mane.plugins.adventure.systems.creatures.brain.memories.EntityMemory;
import org.bukkit.Location;

public abstract class EntityBrain {

    protected EntityController controller;
    protected EntityMemory memory;

    public EntityBrain(Creature creature){
        controller = new EntityController(creature);
        this.memory = new CreatureMemory();
    }

    public EntityController getController() {
        return controller;
    }

    public void setTargetMovement(Location location){
        this.controller.setTargetMovement(location);
    }

    public void setTargetLook(Location location){
        this.controller.setTargetLook(location);
    }

    public void setTarget(Creature creature){
        this.controller.setTarget(creature);
    }

    public abstract boolean canSeeEntity(Creature enemy);
}
