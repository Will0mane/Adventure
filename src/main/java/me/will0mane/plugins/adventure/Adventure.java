package me.will0mane.plugins.adventure;

import lombok.Getter;
import me.will0mane.plugins.adventure.game.databases.mongodb.AdventureGameMongoDB;
import me.will0mane.plugins.adventure.systems.commands.register.CommandRegister;
import me.will0mane.plugins.adventure.systems.database.mongodb.MongoDBSettings;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.listeners.DefaultListener;
import me.will0mane.plugins.adventure.systems.listeners.InteractListener;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.listeners.manager.AdventureListenerManager;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Adventure extends JavaPlugin {

    private static String PLUGIN_UID = UUID.randomUUID().toString();
    private static Adventure PLUGIN;
    private static AdventureListenerManager LISTENER_MANAGER;
    private static CommandRegister COMMAND_REGISTER;
    private static AdventureItemHandler ITEM_HANDLER;
    @Getter
    private static AdventureGameMongoDB MongoDB;

    @Override
    public void onEnable() {
        PLUGIN = this;
        LISTENER_MANAGER = new AdventureListenerManager();
        LISTENER_MANAGER.registerDefaults(this);
        COMMAND_REGISTER = new CommandRegister();
        COMMAND_REGISTER.registerDefaults();
        ITEM_HANDLER = new AdventureItemHandler();
        ITEM_HANDLER.registerDefaults();
        MongoDB = new AdventureGameMongoDB(new MongoDBSettings(""));
    }

    @Override
    public void onDisable() {

    }

    public static Adventure getInstance(){
        return PLUGIN;
    }

    public static NamespacedKey getKey(String key) {
        return new NamespacedKey(getInstance(), key);
    }
}
