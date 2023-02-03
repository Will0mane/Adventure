package me.will0mane.plugins.adventure.systems.database.mongodb;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.AdventureDB;
import org.willomane.mongowillo.MongoDB;

public abstract class AdventureMongoDB extends AdventureDB {

    @Getter
    private final MongoDB mongoDB;

    protected AdventureMongoDB(MongoDBSettings settings){
        this.mongoDB = new MongoDB(settings.uri());
    }

}
