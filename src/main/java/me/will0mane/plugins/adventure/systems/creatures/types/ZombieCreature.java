package me.will0mane.plugins.adventure.systems.creatures.types;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.creatures.Creature;
import me.will0mane.plugins.adventure.systems.creatures.brain.EntityBrain;
import me.will0mane.plugins.adventure.systems.creatures.brain.abstraction.CreatureBrain;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

public class ZombieCreature extends Creature {

    private Location spawnLoc;
    private EntityBrain brain;
    private Zombie zombie;
    private PlayerCreature target;
    private UUID owner;

    public ZombieCreature(Location location) {
        super(location);
        this.spawnLoc = location;
        zombie = Objects.requireNonNull(location.getWorld()).spawn(location, Zombie.class);
        new BukkitRunnable(){

            @Override
            public void run() {
                tick();
            }
        }.runTaskTimer(Adventure.getInstance(), 0,1);
    }

    private void tick() {
        if(target == null) {
            for(Entity entity : zombie.getNearbyEntities(5,5,5)){
                if(entity instanceof Player){
                    if(entity.getUniqueId() != owner){
                        PlayerCreature creature = PlayerCreature.get(entity.getUniqueId());
                        target = creature;
                    }
                }
            }
        }
        if(target == null) return;
        if(!getBrain().canSeeEntity(target)) {
            target = null;
            return;
        }
        getBrain().setTarget(target);
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    @Override
    public Location getLocation() {
        return getBukkit().getLocation();
    }

    @Override
    public LivingEntity getBukkit() {
        return zombie;
    }

    @Override
    public EntityLiving getNMS() {
        return ((CraftLivingEntity) getBukkit()).getHandle();
    }
}
