package me.will0mane.plugins.adventure.systems.stats;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.stats.mongodb.MongoProtection;
import me.will0mane.plugins.adventure.game.stats.mongodb.MongoStrength;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.stats.types.ArrayListStatistic;

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
        registerStat("protection", new MongoProtection(mongoDB));
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
    }
}
