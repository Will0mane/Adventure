package me.will0mane.plugins.adventure.systems.creatures.brain.memories.abstraction;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.goals.abstraction.AttackEnemyGoal;
import me.will0mane.plugins.adventure.systems.creatures.brain.memories.EntityMemories;
import org.bukkit.Location;

import java.util.function.Consumer;

public class EnemyTargetLocationMemory extends EntityMemories {

    protected Creature creature;
    protected Location targetLocation;
    protected Creature enemy;

    public EnemyTargetLocationMemory(Creature creature, Creature enemy){
        this.creature = creature;
        this.enemy = enemy;
        this.targetLocation = enemy.getLocation();
    }

    @Override
    public Consumer<Creature> onMemoryRemember() {
        if(creature.getBrain().canSeeEntity(enemy)) return cr ->{
          creature.getBrain().setTarget(enemy);
        };
        return null;
    }
}
