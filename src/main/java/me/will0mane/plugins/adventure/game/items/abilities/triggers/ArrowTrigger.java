package me.will0mane.plugins.adventure.game.items.abilities.triggers;

import me.will0mane.plugins.adventure.game.projectiles.ArrowProjectile;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collections;
import java.util.List;

public class ArrowTrigger extends ItemAbility<InteractAbility> {

    public ArrowTrigger() {
        super("arrow_trigger");
    }

    @Override
    public void trigger(InteractAbility data) {
        PlayerInteractEvent event = data.getEvent();
        if(event.getAction() == Action.LEFT_CLICK_AIR ||
        event.getAction() == Action.LEFT_CLICK_BLOCK ||
        event.getAction() == Action.PHYSICAL) return;
        Player player = event.getPlayer();
        ArrowProjectile arrow = new ArrowProjectile().setDisappearOnHit(true);
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
        return Abilities.ARROW;
    }
}
