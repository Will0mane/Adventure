package me.will0mane.plugins.adventure;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.hologram.Hologram;
import me.will0mane.plugins.adventure.systems.registry.AdventureRegistry;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Adventure extends JavaPlugin {

    private static Adventure plugin;
    @Getter
    private static AdventureRegistry registry;

    @Override
    public void onEnable() {
        plugin = this;

        registry = new AdventureRegistry();
    }

    @Override
    public void onDisable() {
        Hologram.holograms.forEach(Hologram::removeHologram);
        saveUserData();
    }

    private void saveUserData() {
        Bukkit.getOnlinePlayers().forEach(player -> getRegistry().getAdventureStatManager().saveAll(player.getUniqueId()));
    }

    public static Adventure getInstance(){
        return plugin;
    }

    public static NamespacedKey getKey(String key) {
        return new NamespacedKey(getInstance(), key);
    }
}
