package me.will0mane.plugins.adventure.systems.creatures.brain;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.controller.EntityController;
import me.will0mane.plugins.adventure.systems.creatures.brain.goals.EntityGoal;
import me.will0mane.plugins.adventure.systems.creatures.brain.memories.CreatureMemory;
import me.will0mane.plugins.adventure.systems.creatures.brain.memories.EntityMemory;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EntityBrain {

    private EntityController controller;
    private EntityMemory memory;
    private Map<Integer, List<EntityGoal>> goals;

    protected EntityBrain(Creature creature){
        controller = new EntityController(creature);
        this.memory = new CreatureMemory();
        this.goals = new HashMap<>();
    }

    public EntityController getController() {
        return controller;
    }

    public void setTargetMovement(Location location, double speed){
        this.controller.setTargetMovement(location, speed);
    }

    public void addGoal(int priority, EntityGoal goal){
        List<EntityGoal> goalForPriority = goals.get(priority);
        goalForPriority.add(goal);
        goals.put(priority, goalForPriority);
    }

    public void setTargetLook(Location location){
        this.controller.setTargetLook(location);
    }

    public void setTarget(Creature creature){
        this.controller.setTarget(creature);
    }

    public void jump(){
        this.controller.jump();
    }

    public abstract boolean canSeeEntity(Creature enemy);
}
