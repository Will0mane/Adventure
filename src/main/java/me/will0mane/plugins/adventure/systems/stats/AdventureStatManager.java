package me.will0mane.plugins.adventure.systems.stats;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.stats.mongodb.*;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.profile.AdventureProfile;
import me.will0mane.plugins.adventure.systems.profile.equipment.AdventureEquipment;
import me.will0mane.plugins.adventure.systems.stats.types.ArrayListStatistic;
import me.will0mane.plugins.adventure.systems.stats.types.EquipmentStat;

import java.util.*;

public class AdventureStatManager {

    private final Map<String, AdventureStat<?>> stats = new HashMap<>();

    public void registerStat(String id, AdventureStat<?> stat){
        stats.put(id, stat);
    }

    public Optional<AdventureStat<?>> getRegisteredStatistic(String id){
        if(!stats.containsKey(id)) return Optional.empty();
        return Optional.of(stats.get(id));
    }

    public void registerDefaultsMongo(){
        AdventureMongoDB mongoDB = Adventure.getRegistry().getMongoDB();
        registerStat("strength", new MongoStrength(mongoDB));
        registerStat("critDamage", new MongoCritDamage(mongoDB));
        registerStat("protection", new MongoProtection(mongoDB));
        registerStat("pets", new MongoPets(mongoDB));
        registerStat("player_equipment", new MongoEquipment(mongoDB));
    }

    public void saveAll(UUID uniqueId) {
        getRegisteredStatistic("pets").ifPresent(stat -> {
            ArrayListStatistic<String> statistic = (ArrayListStatistic<String>) stat;
            List<String> neu = new ArrayList<>();
            AdventurePet.loadedPets.forEach((uuid, adventurePets) -> adventurePets.forEach(adventurePet -> {
                neu.add(adventurePet.serialize());
            }));
            statistic.setList(uniqueId, neu);
        });
//        getRegisteredStatistic("player_equipment").ifPresent(stat -> {
//            EquipmentStat<AdventureItem> statistic = (EquipmentStat<AdventureItem>) stat;
//            AdventureProfile profile = AdventureProfile.getProfile(uniqueId);
//            AdventureEquipment equipment = profile.getEquipment();
//            Map<String, AdventureItem> map = new HashMap<>();
//            map.put("head", equipment.getHeadStack());
//            map.put("chest", equipment.getChestStack());
//            map.put("belt", equipment.getBeltStack());
//            map.put("feet", equipment.getFeetStack());
//
//            statistic.setAll(uniqueId, map);
//        });
    }
}
