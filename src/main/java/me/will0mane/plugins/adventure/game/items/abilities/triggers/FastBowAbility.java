package me.will0mane.plugins.adventure.game.items.abilities.triggers;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.projectiles.ArrowProjectile;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FastBowAbility extends ItemAbility<InteractAbility> {

    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    public FastBowAbility() {
        super("fast_bow");
    }


    @Override
    public void trigger(InteractAbility data) {
        PlayerInteractEvent event = data.getEvent();
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK ||
                event.getAction() == Action.RIGHT_CLICK_AIR ||
                event.getAction() == Action.PHYSICAL) return;
        if(cooldownMap.containsKey(player.getUniqueId())){
            long millisCooldown = cooldownMap.get(player.getUniqueId());
            long millisLeft = ((millisCooldown + 10000)- System.currentTimeMillis());
            long seconds = millisLeft / 1000;
            if((System.currentTimeMillis() - millisCooldown) > 10000){
                cooldownMap.remove(player.getUniqueId());
            }else {
                ChatUtils.sendTranslatedActionBar(player, "%%red%%This ability is on cooldown! " + seconds + "s left");
                return;
            }
        }
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        int taskId = new BukkitRunnable(){

            @Override
            public void run() {
                ArrowProjectile arrow = new ArrowProjectile().setDisappearOnHit(true);
                arrow.shoot(player, 0);
            }
        }.runTaskTimer(Adventure.getInstance(), 1,3).getTaskId();
        new BukkitRunnable(){

            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(taskId);
            }
        }.runTaskLater(Adventure.getInstance(), 60);
    }

    @Override
    public String getName() {
        return "FAST BOW";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("&7Fires a bunch of", "&7arrows.", "&bCooldown: 10s");
    }

    @Override
    public String activationMethodName() {
        return "ON INTERACT";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.FAST_BOW;
    }
}
