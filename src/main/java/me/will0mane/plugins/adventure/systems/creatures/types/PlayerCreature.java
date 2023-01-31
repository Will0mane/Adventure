package me.will0mane.plugins.adventure.systems.creatures.types;

import me.will0mane.plugins.adventure.systems.creatures.Creature;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PlayerCreature extends Creature {

    private static Map<UUID, PlayerCreature> creatureMap = new HashMap<>();
    private Player player;

    protected PlayerCreature(Player player) {
        super(player.getLocation());
        this.player = player;
        creatureMap.put(player.getUniqueId(), this);
    }

    public static PlayerCreature get(UUID uniqueId) {
        creatureMap.computeIfAbsent(uniqueId, uuid -> new PlayerCreature(Objects.requireNonNull(Bukkit.getPlayer(uniqueId))));
        return creatureMap.get(uniqueId);
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public LivingEntity getBukkit() {
        return player;
    }

    @Override
    public EntityLiving getNMS() {
        return ((CraftLivingEntity) player).getHandle();
    }
}
