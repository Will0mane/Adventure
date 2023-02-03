package me.will0mane.plugins.adventure.systems.database.mysql;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.database.AdventureDB;

import java.sql.Connection;

public abstract class AdventureMySQL extends AdventureDB{

    @Getter
    private final Connection connection;

    protected AdventureMySQL (Connection connection){
        this.connection = connection;
    }

}
