package me.will0mane.plugins.adventure.systems.stats.modes;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

public enum StatMode implements ConfigurationSerializable , Serializable {

    HOLD,
    ARMOR;

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of("value", name());
    }
}
