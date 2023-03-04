package me.will0mane.plugins.adventure.systems.stats.types;

import me.will0mane.plugins.adventure.systems.stats.AdventureStat;

import java.util.Map;
import java.util.UUID;

public abstract class EquipmentStat<T> extends AdventureStat<T> {

    public abstract Map<String, T> getAll(UUID uuid);
    public abstract T getAtPosition(UUID uuid, String position);
    public abstract void setAtPosition(UUID uuid, String position, T itemStack);
    public abstract void setAll(UUID uuid, Map<String,T> map);

}
