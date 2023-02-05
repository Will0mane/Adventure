package me.will0mane.plugins.adventure.systems.sessions;

import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SessionHandler {

    private final Map<UUID, PlayerSession> sessionMap = new HashMap<>();

    public void registerSession(UUID uuid){
        registerSession(uuid, new PlayerSession(uuid));
    }

    public void registerSession(UUID uuid, PlayerSession session){
        sessionMap.put(uuid, session);
    }

    public Optional<PlayerSession> get(UUID uuid){
        if(sessionMap.containsKey(uuid)) return Optional.of(sessionMap.get(uuid));
        return Optional.empty();
    }

}
