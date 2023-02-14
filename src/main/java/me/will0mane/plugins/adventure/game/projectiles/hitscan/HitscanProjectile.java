package me.will0mane.plugins.adventure.game.projectiles.hitscan;

import me.will0mane.plugins.adventure.game.projectiles.hitscan.laser.Laser;
import me.will0mane.plugins.adventure.systems.executors.Executor;
import me.will0mane.plugins.adventure.systems.executors.hash.HashExecutor;
import me.will0mane.plugins.adventure.systems.projectiles.AdventureProjectile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class HitscanProjectile extends AdventureProjectile {

    private final double raySize;
    private final double damage;
    private Location start;
    private Location end;
    private final Consumer<Block> hitBlock;
    private final Consumer<Entity> hitEntity;
    private Laser laser;

    public HitscanProjectile(double raySize, double damage, Consumer<Block> hitBlock, Consumer<Entity> hitEntity){
        this.raySize = raySize;
        this.damage = damage;
        this.hitBlock = hitBlock;
        this.hitEntity = hitEntity;
    }

    @Override
    public void shoot(Player player, double speed) {
        start = player.getLocation();
        Vector dir = start.getDirection();
        end = start.clone().add(dir.multiply(speed));
        laser = new Laser(start, end, dir, (float) speed, raySize, damage);
        List<Block> blocks = laser.rayCastBlocks();
        Collection<Entity> entities = laser.rayCastEntities();
        if(blocks != null && !blocks.isEmpty()){
            blocks.forEach(this::onHitBlock);
        }
        if(entities != null && !entities.isEmpty()){
            Bukkit.broadcastMessage("something");
            entities.forEach(entity -> {
                Bukkit.broadcastMessage("entity");
                onHitEntity(entity);
            });
        }
    }

    public void highlight(String particle, double accuracy, double data){
        if(getLaser() == null) return;
        getLaser().highlight(particle, accuracy, start.distance(end), data);
    }

    public Laser getLaser() {
        return laser;
    }

    @Override
    public void onHitBlock(Block block) {
        if(hitBlock != null) hitBlock.accept(block);
    }

    @Override
    public void onHitEntity(Entity entity) {
        Bukkit.broadcastMessage("hitentity");
        if(hitEntity != null) hitEntity.accept(entity);
    }

    @Override
    public void destroy() {
    }
}
