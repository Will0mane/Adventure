package me.will0mane.plugins.adventure.systems.stats;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.stats.mongodb.MongoProtection;
import me.will0mane.plugins.adventure.game.stats.mongodb.MongoStrength;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        registerStat("strength", new MongoStrength(Adventure.getMongoDB()));
        registerStat("protection", new MongoProtection(Adventure.getMongoDB()));
    }

}
