package me.will0mane.plugins.adventure.game.stats.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.stats.types.AmountStatistic;
import org.bson.Document;
import org.willomane.mongowillo.MongoDB;

import java.util.UUID;

public class MongoStrength extends AmountStatistic<Double> {

    private static final String DB_KEY = "strength";
    private static final String DB_PLAYER_UUID = "playerUUID";

    @Getter
    private final AdventureMongoDB mongoDB;
    private final MongoCollection<Document> statsDocument;

    public MongoStrength(AdventureMongoDB mongoDB){
        this.mongoDB = mongoDB;
        MongoDB willoMongoDB = mongoDB.getMongoDB();
        MongoDatabase playerDatabase = willoMongoDB.getDatabase("player_data");
        this.statsDocument = playerDatabase.getCollection("stats");
    }

    @Override
    public Double get(UUID uuid) {
        FindIterable<Document> documents = statsDocument.find(new Document(DB_PLAYER_UUID, uuid.toString()));

        if(!documents.iterator().hasNext()) return 0D;

        Document document = documents.first();

        if(document == null) return 0D;

        return document.getDouble(DB_KEY);
    }

    @Override
    public void set(UUID uuid, Double value) {
        FindIterable<Document> documents = statsDocument.find(new Document(DB_PLAYER_UUID, uuid.toString()));

        if(!documents.iterator().hasNext()) {
            statsDocument.insertOne(new Document(DB_PLAYER_UUID, uuid.toString()));
            set(uuid, value);
            return;
        }

        Document document = documents.first();

        if(document == null) return;

        document.remove(DB_KEY);
        document.put(DB_KEY, value);
        statsDocument.replaceOne(document, document);
    }

    @Override
    public void add(UUID uuid, Double value) {
        double now = get(uuid);
        set(uuid, now + value);
    }

    @Override
    public void subtract(UUID uuid, Double value) {
        double now = get(uuid);
        set(uuid, now - value);
    }

    @Override
    public void multiply(UUID uuid, Double value) {
        double now = get(uuid);
        set(uuid, now * value);
    }

    @Override
    public void divide(UUID uuid, Double value) {
        double now = get(uuid);
        set(uuid, now / value);
    }
}
