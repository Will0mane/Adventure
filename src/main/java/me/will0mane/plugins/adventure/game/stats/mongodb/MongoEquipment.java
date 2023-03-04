package me.will0mane.plugins.adventure.game.stats.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.stats.types.EquipmentStat;
import org.bson.Document;
import org.willomane.mongowillo.MongoDB;

import java.util.*;

public class MongoEquipment extends EquipmentStat<AdventureItem> {

    private static final String DB_KEY = "equipment";
    private static final String DB_PLAYER_UUID = "playerUUID";

    @Getter
    private final AdventureMongoDB mongoDB;
    private final MongoCollection<Document> statsDocument;

    public MongoEquipment(AdventureMongoDB mongoDB){
        this.mongoDB = mongoDB;
        MongoDB willoMongoDB = mongoDB.getMongoDB();
        MongoDatabase playerDatabase = willoMongoDB.getDatabase("player_data");
        this.statsDocument = playerDatabase.getCollection("stats");
    }

    @Override
    public AdventureItem get(UUID uuid) {
        return getAll(uuid).values().stream().findFirst().get();
    }

    @Override
    public String getAsString(UUID uuid) {
        return get(uuid).toString();
    }

    @Override
    public void set(UUID uuid, AdventureItem value) {
        setAtPosition(uuid, "unknown", value);
    }

    @Override
    public void setAsString(UUID uuid, String string) {
        setAtPosition(uuid, "unknown", null);
    }

    @Override
    public String inGameName() {
        return "Equipment";
    }

    @Override
    public String symbol() {
        return "";
    }

    @Override
    public Map<String, AdventureItem> getAll(UUID uuid) {
        FindIterable<Document> documents = statsDocument.find(new Document(DB_PLAYER_UUID, uuid.toString()));

        if(!documents.iterator().hasNext()) return new HashMap<>();

        Document document = documents.first();

        if(document == null) return new HashMap<>();

        Map<String, AdventureItem> equipment = document.get(DB_KEY, Map.class);

        if(equipment == null){
            return new HashMap<>();
        }

        return equipment;
    }

    @Override
    public AdventureItem getAtPosition(UUID uuid, String position) {
        return getAll(uuid).get(position);
    }

    @Override
    public void setAtPosition(UUID uuid, String position, AdventureItem itemStack) {
        Map<String, AdventureItem> map = getAll(uuid);
        map.put(position, itemStack);
        setAll(uuid, map);
    }

    @Override
    public void setAll(UUID uuid, Map<String, AdventureItem> map) {
        Document filter = new Document(DB_PLAYER_UUID, uuid.toString());
        FindIterable<Document> documents = statsDocument.find(filter);

        if(!documents.iterator().hasNext()) {
            statsDocument.insertOne(new Document(DB_PLAYER_UUID, uuid.toString()));
            setAll(uuid, map);
            return;
        }

        Document document = documents.first();

        if(document == null) return;
        document.put(DB_KEY, map);

        statsDocument.replaceOne(filter, document);
    }
}
