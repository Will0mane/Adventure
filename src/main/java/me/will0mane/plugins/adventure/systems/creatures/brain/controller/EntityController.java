package me.will0mane.plugins.adventure.systems.creatures.brain.controller;

import com.mojang.math.Matrix3f;
import me.will0mane.plugins.adventure.systems.creatures.Creature;
import net.minecraft.world.entity.EntityInsentient;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Objects;

public class EntityController {

    private Location targetMovement;
    private Location targetLook;
    private Creature target;
    private Creature brain;
    private boolean isInAir = false;

    public EntityController(Creature creature){
        this.targetMovement = creature.getLocation();
        this.targetLook = creature.getLocation();
        this.target = creature;
        this.brain = creature;
    }

    public void setTarget(Creature target) {
        this.target = target;
        Objects.requireNonNull(this.insentient()).setGoalTarget(target.getNMS(), EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY, true);
    }

    private EntityInsentient insentient() {
        if(brain == null || !(brain.getBukkit() instanceof EntityInsentient)) return null;
        return (EntityInsentient) brain.getBukkit();
    }

    public void setTargetLook(Location targetLook) {
        this.targetLook = targetLook;
        Objects.requireNonNull(this.insentient()).getControllerLook().a(targetLook.getX(), targetLook.getY(), targetLook.getZ());
    }

    public void setTargetMovement(Location targetMovement, double speed) {
        this.targetMovement = targetMovement;
        Objects.requireNonNull(this.insentient()).getControllerMove().a(targetMovement.getX(), targetMovement.getY(), targetMovement.getZ(), speed);
    }

    public void jump(){
        this.isInAir = true;
        Objects.requireNonNull(this.insentient()).getControllerJump().jump();
    }
}
