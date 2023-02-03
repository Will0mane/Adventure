package me.will0mane.plugins.adventure.game.databases.mongodb;

import com.mongodb.client.MongoClient;
import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.database.mongodb.MongoDBSettings;

public class AdventureGameMongoDB extends AdventureMongoDB {

    @Getter
    private final MongoClient client;

    public AdventureGameMongoDB(MongoDBSettings settings) {
        super(settings);
        super.getMongoDB().connect();
        client = super.getMongoDB().getClient();
    }
}
