package me.will0mane.plugins.adventure.systems.stats.types;

import me.will0mane.plugins.adventure.systems.stats.AdventureStat;

import java.util.List;
import java.util.UUID;

public abstract class ArrayListStatistic<T> extends AdventureStat<T> {

    public abstract List<T> getList(UUID uuid);
    public abstract void addEntry(UUID uuid, T entry);
    public abstract void removeEntry(UUID uuid, T entry);
    public abstract void setList(UUID uuid, List<T> list);

}
