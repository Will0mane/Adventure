package me.will0mane.plugins.adventure.game.items.abilities.triggers;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.projectiles.ArrowProjectile;
import me.will0mane.plugins.adventure.game.projectiles.hitscan.HitscanProjectile;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.*;

public class SniperHitAbility extends ItemAbility<InteractAbility> {

    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    public SniperHitAbility() {
        super("sniper_hit");
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
            if((System.currentTimeMillis() - millisCooldown) > 1000){
                cooldownMap.remove(player.getUniqueId());
            }else {
                ChatUtils.sendTranslatedActionBar(player, "%%red%%This ability is on cooldown!");
                return;
            }
        }
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        HitscanProjectile hitscanProjectile = new HitscanProjectile(0.5, 0.001, 50, null, entity -> {
            if(entity instanceof Damageable) ((Damageable) entity).damage(50);
        });
        hitscanProjectile.shoot(data.getEvent().getPlayer(), 120);
        hitscanProjectile.highlight("CRIT,1", 1, 0.01, 0);
        SoundUtils.playSoundYaml("ENTITY_DRAGON_FIREBALL_EXPLODE,1,1", data.getEvent().getPlayer());
    }

    @Override
    public String getName() {
        return "SNIPER";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("&7Fires a projectile", "&7that deals &c50 &7damage", "&7on impact.");
    }

    @Override
    public String activationMethodName() {
        return "ON LEFT CLICK";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.SNIPER_HIT;
    }
}
