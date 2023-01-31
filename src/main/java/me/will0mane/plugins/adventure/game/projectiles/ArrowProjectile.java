package me.will0mane.plugins.adventure.game.projectiles;

import me.will0mane.plugins.adventure.systems.projectiles.AdventureProjectile;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowProjectile extends AdventureProjectile {

    protected Arrow arrow;
    protected boolean disappearOnHit = false;

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

    @Override
    public void onHitBlock(Block block) {
        if(disappearOnHit && arrow != null) arrow.remove();
    }

    @Override
    public void onHitEntity(Entity entity) {
        if(disappearOnHit && arrow != null) arrow.remove();
    }

    @Override
    public void destroy() {
        if(arrow == null) return;
        arrow.remove();
    }
}
