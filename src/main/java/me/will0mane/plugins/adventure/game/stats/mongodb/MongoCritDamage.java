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

public class MongoCritDamage extends AmountStatistic<Double> {

    private static final String DB_KEY = "critDamage";
    private static final String DB_PLAYER_UUID = "playerUUID";

    @Getter
    private final AdventureMongoDB mongoDB;
    private final MongoCollection<Document> statsDocument;

    public MongoCritDamage(AdventureMongoDB mongoDB){
        this.mongoDB = mongoDB;
        MongoDB willoMongoDB = mongoDB.getMongoDB();
        MongoDatabase playerDatabase = willoMongoDB.getDatabase("player_data");
        this.statsDocument = playerDatabase.getCollection("stats");
    }

    @Override
    public Double get(UUID uuid) {
        FindIterable<Document> documents = statsDocument.find(new Document(DB_PLAYER_UUID, uuid.toString()));

        if(!documents.iterator().hasNext()) return 1D;

        Document document = documents.first();

        if(document == null) return 1D;

        return document.getDouble(DB_KEY) == null ? 1D : document.getDouble(DB_KEY);
    }

    @Override
    public String getAsString(UUID uuid) {
        return "" + get(uuid);
    }


    @Override
    public void setAsString(UUID uuid, String string) {
        set(uuid, Double.parseDouble(string));
    }

    @Override
    public String inGameName() {
        return "Crit Damage";
    }

    @Override
    public String symbol() {
        return "&9☠";
    }

    @Override
    public void set(UUID uuid, Double value) {
        Document filter = new Document(DB_PLAYER_UUID, uuid.toString());
        FindIterable<Document> documents = statsDocument.find(filter);

        if(!documents.iterator().hasNext()) {
            statsDocument.insertOne(new Document(DB_PLAYER_UUID, uuid.toString()));
            set(uuid, value);
            return;
        }

        Document document = documents.first();

        if(document == null) return;
        document.append(DB_KEY, value);

        statsDocument.replaceOne(filter, document);
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