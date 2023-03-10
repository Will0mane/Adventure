package me.will0mane.plugins.adventure.systems.stats.modes;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

public class StatData implements ConfigurationSerializable, Serializable {

    @Getter
    private final double value;
    @Getter
    private final StatMode mode;

    public StatData(double value, StatMode mode){
        this.value = value;
        this.mode = mode;
    }

    @Override
    public String toString(){
        return mode.name() + ":" + value;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of("data", value + ";" + mode.name());
    }
}
