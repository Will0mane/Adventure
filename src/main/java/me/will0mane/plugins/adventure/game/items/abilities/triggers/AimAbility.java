package me.will0mane.plugins.adventure.game.items.abilities.triggers;

import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AimAbility extends ItemAbility<InteractAbility> {

    private final List<UUID> aimedPlayers = new ArrayList<>();

    public AimAbility() {
        super("aim");
    }

    @Override
    public void trigger(InteractAbility data) {
        PlayerInteractEvent event = data.getEvent();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(event.getAction() == Action.LEFT_CLICK_BLOCK ||
                event.getAction() == Action.LEFT_CLICK_AIR ||
                event.getAction() == Action.PHYSICAL) return;
        if(aimedPlayers.contains(uuid)){
            player.removePotionEffect(PotionEffectType.SLOW);
            aimedPlayers.remove(uuid);
        }else {
            aimedPlayers.add(uuid);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 5));
        }
    }

    @Override
    public String getName() {
        return "AIM";
    }

    @Override
    public List<String> getDescription() {
        return Collections.singletonList("&7Toggles aim");
    }

    @Override
    public String activationMethodName() {
        return "ON RIGHT CLICK";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.AIM;
    }
}
