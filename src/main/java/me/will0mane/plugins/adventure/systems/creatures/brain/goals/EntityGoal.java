package me.will0mane.plugins.adventure.systems.creatures.brain.goals;

import me.will0mane.plugins.adventure.systems.creatures.Creature;

public abstract class EntityGoal {

    private Creature creature;

    public EntityGoal(Creature creature){
        this.creature = creature;
    }

    public abstract void tick();
    public abstract void trigger();
    public abstract void destroy();

}
