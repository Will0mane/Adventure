package me.will0mane.plugins.adventure.systems.creatures.brain.abstraction;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.EntityBrain;

public class CreatureBrain extends EntityBrain {

    protected final Creature creature;

    public CreatureBrain(Creature creature) {
        super(creature);
        this.creature = creature;
    }

    @Override
    public boolean canSeeEntity(Creature enemy) {
        return creature.getBukkit().hasLineOfSight(enemy.getBukkit());
    }
}
