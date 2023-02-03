package me.will0mane.plugins.adventure.systems.stats.types;

import me.will0mane.plugins.adventure.systems.stats.AdventureStat;

import java.util.UUID;

public abstract class AmountStatistic<T> extends AdventureStat<T> {

    public abstract void add(UUID uuid, T value);
    public abstract void subtract(UUID uuid, T value);
    public abstract void multiply(UUID uuid, T value);
    public abstract void divide(UUID uuid, T value);

}
