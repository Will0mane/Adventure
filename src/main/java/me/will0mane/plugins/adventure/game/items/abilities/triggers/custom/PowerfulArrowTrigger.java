package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.projectiles.ArrowProjectile;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PowerfulArrowTrigger extends ItemAbility<InteractAbility> {

    public PowerfulArrowTrigger() {
        super("powerful_arrow_trigger");
    }

    @Override
    public void trigger(InteractAbility data) {
        PlayerInteractEvent event = data.getEvent();
        if(event.getAction() == Action.LEFT_CLICK_AIR ||
                event.getAction() == Action.LEFT_CLICK_BLOCK ||
                event.getAction() == Action.PHYSICAL) return;
        Player player = event.getPlayer();

        Optional<AdventureStat<?>> stat = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic("strength");
        double strength = stat.isEmpty() ? 1 : Double.parseDouble(stat.get().getAsString(player.getUniqueId())) + 1;

        ArrowProjectile arrow = new ArrowProjectile().setDisappearOnHit(true).setOnHit(arr -> {
            Location location = arr.getLocation();
            Objects.requireNonNull(location.getWorld()).
                    spawnParticle(Particle.EXPLOSION_LARGE, location, 1);
        }).setHitEntity((arr, entity) -> {
            if(entity instanceof Damageable damageable){
                damageable.damage(strength * 1.3);
            }
        });
        arrow.shoot(player, 0);
    }

    @Override
    public String getName() {
        return "ARROW";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("&7Creates an arrow.");
    }

    @Override
    public String activationMethodName() {
        return "ON INTERACT";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.POWERFUL_ARROW;
    }
}
