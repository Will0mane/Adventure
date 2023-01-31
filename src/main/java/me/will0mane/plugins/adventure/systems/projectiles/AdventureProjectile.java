package me.will0mane.plugins.adventure.systems.projectiles;

import me.will0mane.plugins.adventure.Adventure;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class AdventureProjectile implements Listener {

    public AdventureProjectile(){
        Bukkit.getPluginManager().registerEvents(this, Adventure.getInstance());
    }

    public abstract void shoot(Player player, double speed);
    public abstract void onHitBlock(Block block);
    public abstract void onHitEntity(Entity entity);
    public abstract void destroy();

}
