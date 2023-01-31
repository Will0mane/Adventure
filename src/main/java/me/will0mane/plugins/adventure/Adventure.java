package me.will0mane.plugins.adventure;

import me.will0mane.plugins.adventure.systems.commands.register.CommandRegister;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.listeners.DefaultListener;
import me.will0mane.plugins.adventure.systems.listeners.InteractListener;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Adventure extends JavaPlugin {

    private static String PLUGIN_UID;
    private static Adventure PLUGIN;
    private static DefaultListener DEFAULT_LISTENER;
    private static InteractListener INTERACT_LISTENER;
    private static CommandRegister COMMAND_REGISTER;
    private static AdventureItemHandler ITEM_HANDLER;

    @Override
    public void onEnable() {
        PLUGIN_UID = UUID.randomUUID().toString();
        PLUGIN = this;
        DEFAULT_LISTENER = new DefaultListener(this);
        INTERACT_LISTENER = new InteractListener(this);
        COMMAND_REGISTER = new CommandRegister();
        COMMAND_REGISTER.registerDefaults();
        ITEM_HANDLER = new AdventureItemHandler();
        ITEM_HANDLER.registerDefaults();
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
