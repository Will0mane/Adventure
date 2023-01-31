package me.will0mane.plugins.adventure.systems.creatures.brain.goals.abstraction;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.goals.EntityGoal;

public class AttackEnemyGoal extends EntityGoal {

    private Creature enemy;
    private boolean isActive;

    public AttackEnemyGoal(Creature creature, Creature enemy) {
        super(creature);
        this.enemy = enemy;
    }

    @Override
    public void tick() {

    }

    @Override
    public void trigger() {

    }

    @Override
    public void destroy() {

    }
}
