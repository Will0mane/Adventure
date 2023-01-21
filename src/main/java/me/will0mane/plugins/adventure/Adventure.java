package me.will0mane.plugins.adventure;

import me.will0mane.plugins.adventure.systems.listeners.DefaultListener;
import me.will0mane.plugins.adventure.systems.listeners.InteractListener;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Adventure extends JavaPlugin {

    protected static String PLUGIN_UID;
    protected static Adventure PLUGIN;
    protected static DefaultListener DEFAULT_LISTENER;
    protected static InteractListener INTERACT_LISTENER;

    @Override
    public void onEnable() {
        PLUGIN_UID = UUID.randomUUID().toString();
        PLUGIN = this;
        DEFAULT_LISTENER = new DefaultListener(this);
        INTERACT_LISTENER = new InteractListener(this);
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
