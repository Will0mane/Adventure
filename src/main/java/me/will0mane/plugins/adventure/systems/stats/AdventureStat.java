package me.will0mane.plugins.adventure.systems.stats;

import java.util.UUID;

public abstract class AdventureStat<T> {

    public abstract T get(UUID uuid);
    public abstract void set(UUID uuid, T value);

}
