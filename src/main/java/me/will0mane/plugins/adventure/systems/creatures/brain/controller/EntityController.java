package me.will0mane.plugins.adventure.systems.creatures.brain.controller;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import org.bukkit.Location;

public class EntityController {

    protected Location targetMovement;
    protected Location targetLook;
    protected Creature target;

    public EntityController(Creature creature){
        this.targetMovement = creature.getLocation();
        this.targetLook = creature.getLocation();
        this.target = creature;
    }

    public void setTarget(Creature target) {
        this.target = target;
    }

    public void setTargetLook(Location targetLook) {
        this.targetLook = targetLook;
    }

    public void setTargetMovement(Location targetMovement) {
        this.targetMovement = targetMovement;
    }
}
