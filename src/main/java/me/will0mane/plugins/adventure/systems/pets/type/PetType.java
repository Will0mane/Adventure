package me.will0mane.plugins.adventure.systems.pets.type;

import me.will0mane.plugins.adventure.systems.genericevents.GenericEvent;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import org.bukkit.Particle;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public enum PetType {

    //COMMON
    TIGER("Tiger", AdventureRarity.COMMON, Particle.CRIT,
            Arrays.asList(
                    "&7A combat pet:",
                    "&7Deal &cdamage &7to gain &bXP"
            ),
            Collections.singletonList(
                    (pet, genericEvent) -> {
                        Event event = genericEvent.getBukkitEvent();
                        if (event instanceof EntityDamageByEntityEvent damageEvent && damageEvent.getDamager().getUniqueId() == pet.getOwner()) {
                            pet.gainXP(0.1 * damageEvent.getFinalDamage());
                        }
                    }
            ),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTM4MTM3ZWEzMTI4NmJiMGEyNGI4YTZkYjkxZmMwMWVlMGJiYWQ4NTFkNWUxOGFmMGViZTI5YTk3ZTcifX19"),

    //EPIC
    ENDERMAN_EVOKER("Enderman Evoker", AdventureRarity.EPIC, Particle.END_ROD,
            Arrays.asList(
                    "&7A movement pet:",
                    "&7Walk to gain &bXP"
            ),
            Collections.singletonList(
                    (pet, genericEvent) -> {
                        Event event = genericEvent.getBukkitEvent();
                        if (event instanceof PlayerMoveEvent moveEvent && moveEvent.getPlayer().getUniqueId() == pet.getOwner()) {
                            pet.gainXP(0.1 * moveEvent.getFrom().distance(Objects.requireNonNull(moveEvent.getTo())));
                        }
                    }
            ),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE1MTlmNmZjYjU5ZDQwYzdmODg4MDkyOTViMTM5ZmIzMDAyOTVhZDQ1MGVmNmU4YTRlMGYwYWNjYTBkZmJkZCJ9fX0="),
    ;

    private final String headValue;
    private final String name;
    private final AdventureRarity rarity;
    private final Particle particle;
    private final List<String> lore;
    private final List<BiConsumer<AdventurePet, GenericEvent>> consumers;

    PetType(String name, AdventureRarity rarity, Particle particle, List<String> lore,List<BiConsumer<AdventurePet, GenericEvent>> consumers, String headValue) {
        this.name = name;
        this.rarity = rarity;
        this.particle = particle;
        this.lore = lore;
        this.consumers = consumers;
        this.headValue = headValue;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getHeadValue() {
        return headValue;
    }

    public AdventureRarity getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public Particle getParticle() {
        return particle;
    }

    public List<BiConsumer<AdventurePet, GenericEvent>> getConsumers() {
        return consumers;
    }
}
