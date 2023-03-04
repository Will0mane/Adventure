package me.will0mane.plugins.adventure.systems.profile;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.profile.equipment.AdventureEquipment;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.types.EquipmentStat;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AdventureProfile implements ConfigurationSerializable {

    private static final Map<UUID, AdventureProfile> profiles = new HashMap<>();

    //Static
    public static AdventureProfile getProfile(UUID uuid){
        if(!profiles.containsKey(uuid)) loadProfile(uuid);
        return profiles.get(uuid);
    }

    private static void loadProfile(UUID uuid) {
        if(profiles.containsKey(uuid)) return;

        Optional<AdventureStat<?>> optionalEquipment = getStatistic("player_equipment");
        if(optionalEquipment.isEmpty()) return;
        EquipmentStat<AdventureItem> equipmentStat = (EquipmentStat<AdventureItem>) optionalEquipment.get();

        AdventureEquipment equipment = new AdventureEquipment(
                equipmentStat.getAtPosition(uuid, "head"),
                equipmentStat.getAtPosition(uuid, "chest"),
                equipmentStat.getAtPosition(uuid, "belt"),
                equipmentStat.getAtPosition(uuid, "feet"));
        AdventureProfile profile = new AdventureProfile(uuid, equipment);
        profiles.put(uuid, profile);
    }

    private final AdventureEquipment equipment;
    private final UUID player;

    public AdventureProfile(UUID uuid){
        this.player = uuid;
        this.equipment = new AdventureEquipment();
    }

    public AdventureProfile(UUID uuid, AdventureEquipment equipment){
        this.player = uuid;
        this.equipment = equipment;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("player", getPlayer().toString());
        objectMap.put("equipment", getEquipment());
        return objectMap;
    }

    public static Optional<AdventureStat<?>> getStatistic(String id){
        return Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(id);
    }

    public AdventureEquipment getEquipment() {
        return equipment;
    }

    public UUID getPlayer() {
        return player;
    }
}
