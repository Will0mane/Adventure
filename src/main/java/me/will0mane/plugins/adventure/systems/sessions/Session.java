package me.will0mane.plugins.adventure.systems.sessions;

import lombok.Getter;

import java.util.UUID;

public abstract class Session {

    @Getter
    private final UUID uuid = UUID.randomUUID();
    @Getter
    private boolean valid = true;

    public abstract void terminate();
    public abstract void pause();
    public abstract void retry();

    public void validate(){
        this.valid = true;
    }
    public void invalidate(){
        this.valid = false;
    }
}
