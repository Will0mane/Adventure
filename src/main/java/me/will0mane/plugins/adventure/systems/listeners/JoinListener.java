package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.debug.DebugHandler;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.UUID;

public class JoinListener extends AdventureListener {

    public JoinListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        UUID uuid = event.getPlayer().getUniqueId();
        if(!Adventure.getRegistry().isServerOpen()){
            Adventure.getRegistry().getModeration().kickPlayer(event.getPlayer(), "&cThis server is loading up! Please try again later!");
            return;
        }

        Adventure.getRegistry().getSessionsHandler().registerSession(uuid);
        DebugHandler.sendDebug("&aCreating Session for &b" + uuid.toString() + "&a!");
        AdventurePet.loadPets(uuid);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Optional<PlayerSession> session = Adventure.getRegistry().getSessionsHandler().get(event.getPlayer().getUniqueId());
        if(session.isEmpty()) return;
        PlayerSession playerSession = session.get();
        playerSession.invalidate();
        Adventure.getRegistry().getAdventureStatManager().saveAll(event.getPlayer().getUniqueId());
    }
}
