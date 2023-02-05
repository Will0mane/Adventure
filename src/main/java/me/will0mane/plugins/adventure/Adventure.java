package me.will0mane.plugins.adventure;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.registry.AdventureRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Adventure extends JavaPlugin {

    private static String PLUGIN_UID = UUID.randomUUID().toString();
    private static Adventure PLUGIN;
    @Getter
    private static AdventureRegistry Registry;

    @Override
    public void onEnable() {
        PLUGIN = this;

        Registry = new AdventureRegistry();
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
