package me.will0mane.plugins.adventure.game.stats.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.mongodb.AdventureMongoDB;
import me.will0mane.plugins.adventure.systems.stats.types.ArrayListStatistic;
import org.bson.Document;
import org.willomane.mongowillo.MongoDB;

import java.util.*;

public class MongoPets extends ArrayListStatistic<String> {

    private static final String DB_KEY = "pets";
    private static final String DB_PLAYER_UUID = "playerUUID";

    @Getter
    private final AdventureMongoDB mongoDB;
    private final MongoCollection<Document> statsDocument;

    public MongoPets(AdventureMongoDB mongoDB){
        this.mongoDB = mongoDB;
        MongoDB willoMongoDB = mongoDB.getMongoDB();
        MongoDatabase playerDatabase = willoMongoDB.getDatabase("player_data");
        this.statsDocument = playerDatabase.getCollection("stats");
    }

    @Override
    public String get(UUID uuid) {
        return getAsString(uuid);
    }

    @Override
    public String getAsString(UUID uuid) {
        return getList(uuid).toString();
    }

    @Override
    public void set(UUID uuid, String value) {
        setAsString(uuid, value);
    }

    @Override
    public void setAsString(UUID uuid, String value) {
        String[] s = value.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(s));
        setList(uuid, list);
    }

    @Override
    public List<String> getList(UUID uuid) {
        FindIterable<Document> documents = statsDocument.find(new Document(DB_PLAYER_UUID, uuid.toString()));

        if(!documents.iterator().hasNext()) return Collections.emptyList();

        Document document = documents.first();

        if(document == null) return Collections.emptyList();

        String pets = document.getString(DB_KEY);

        if(pets == null){
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(document.getString(DB_KEY).split(",")));
    }

    @Override
    public void addEntry(UUID uuid, String entry) {
        List<String> strings = getList(uuid);
        strings.add(entry);
        setList(uuid, strings);
    }

    @Override
    public void removeEntry(UUID uuid, String entry) {
        List<String> strings = getList(uuid);
        strings.remove(entry);
        setList(uuid, strings);
    }

    @Override
    public void setList(UUID uuid, List<String> list) {
        StringBuilder builder = new StringBuilder();
        int done = 0;
        for(String s : list){
            if(done != 0) builder.append(",");

            builder.append(s);
            done++;
        }

        Document filter = new Document(DB_PLAYER_UUID, uuid.toString());
        FindIterable<Document> documents = statsDocument.find(filter);

        if(!documents.iterator().hasNext()) {
            statsDocument.insertOne(new Document(DB_PLAYER_UUID, uuid.toString()));
            setList(uuid, list);
            return;
        }

        Document document = documents.first();

        if(document == null) return;
        document.append(DB_KEY, builder.toString());

        statsDocument.replaceOne(filter, document);
    }
}
