package me.will0mane.plugins.adventure.game.databases.mysql;

import me.will0mane.plugins.adventure.game.databases.mysql.sources.MySQLDataSource;
import me.will0mane.plugins.adventure.systems.database.mysql.AdventureMySQL;
import me.will0mane.plugins.adventure.systems.database.mysql.MySQLDatabaseSettings;

import java.sql.SQLException;

public class PlayerVolatileDatabase extends AdventureMySQL {

    public PlayerVolatileDatabase(MySQLDatabaseSettings settings) throws SQLException {
        super(new MySQLDataSource().setup(settings).getConnection());
        MySQLDataSource dataSource = new MySQLDataSource();
        dataSource.setup(settings);
    }
}
