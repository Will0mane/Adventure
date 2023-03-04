package me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapSerializable implements ConfigurationSerializable {

    @Getter
    private final Map<String, Object> map;

    public MapSerializable(Map<String, Object> map){
        this.map = map;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return map;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        map.forEach((s, o) -> builder.append("\n").append("&7- &b").append(s).append(": &a").append(o.toString()));
        return builder.toString();
    }
}
