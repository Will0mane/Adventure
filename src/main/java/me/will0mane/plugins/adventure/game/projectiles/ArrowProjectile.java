package me.will0mane.plugins.adventure.game.projectiles;

import me.will0mane.plugins.adventure.systems.projectiles.AdventureProjectile;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ArrowProjectile extends AdventureProjectile {

    private Arrow arrow;
    private boolean disappearOnHit = false;
    private Consumer<Arrow> onHit;
    private BiConsumer<Arrow, Block> hitBlock;
    private BiConsumer<Arrow, Entity> hitEntity;

    public ArrowProjectile(){
    }

    protected ArrowProjectile(Arrow arrow, boolean disappearOnHit){
        this.arrow = arrow;
        this.disappearOnHit = disappearOnHit;
    }

    public ArrowProjectile setDisappearOnHit(boolean disappearOnHit) {
        this.disappearOnHit = disappearOnHit;
        return this;
    }

    @Override
    public void shoot(Player player, double speed) {
        arrow = player.launchProjectile(Arrow.class);
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e){
        if(arrow == null) return;
        if(e.getEntity().getUniqueId() == arrow.getUniqueId()){
            if(e.getHitEntity() != null){
                onHitEntity(e.getEntity());
            }
            if(e.getHitBlock() != null){
                onHitBlock(e.getHitBlock());
            }
        }
    }

    public ArrowProjectile setHitBlock(BiConsumer<Arrow, Block> hitBlock) {
        this.hitBlock = hitBlock;
        return this;
    }

    public ArrowProjectile setHitEntity(BiConsumer<Arrow, Entity> hitEntity) {
        this.hitEntity = hitEntity;
        return this;
    }

    public ArrowProjectile setOnHit(Consumer<Arrow> onHit) {
        this.onHit = onHit;
        return this;
    }

    @Override
    public void onHitBlock(Block block) {
        if(disappearOnHit && arrow != null) arrow.remove();
        if(onHit != null) onHit.accept(arrow);
        if(hitBlock != null) hitBlock.accept(arrow, block);
    }

    @Override
    public void onHitEntity(Entity entity) {
        if(disappearOnHit && arrow != null) arrow.remove();
        if(onHit != null) onHit.accept(arrow);
        if(hitEntity != null)  hitEntity.accept(arrow, entity);
    }

    @Override
    public void destroy() {
        if(arrow == null) return;
        arrow.remove();
    }
}
