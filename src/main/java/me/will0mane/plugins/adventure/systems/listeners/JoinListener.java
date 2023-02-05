package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class JoinListener extends AdventureListener {

    public JoinListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        Adventure.getRegistry().getSessionsHandler().registerSession(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Optional<PlayerSession> session = Adventure.getRegistry().getSessionsHandler().get(event.getPlayer().getUniqueId());
        if(session.isEmpty()) return;
        PlayerSession playerSession = session.get();
        playerSession.invalidate();
    }
}
