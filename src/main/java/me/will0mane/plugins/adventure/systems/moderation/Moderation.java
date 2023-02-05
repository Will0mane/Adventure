package me.will0mane.plugins.adventure.systems.moderation;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.moderation.durations.Duration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;

public class Moderation extends AdventureListener {

    private static final Map<UUID, Duration> mutedPlayers = new HashMap<>();

    public Moderation() {
        super(Adventure.getInstance());
    }

    public void kickPlayer(UUID uuid, String kickMessage){
        kickPlayer(Objects.requireNonNull(Bukkit.getPlayer(uuid)), kickMessage);
    }

    public void kickPlayer(Player player, String kickMessage){
        player.kickPlayer(ChatUtils.translate(kickMessage));
    }

    public void mutePlayer(UUID uuid){
        mutedPlayers.put(uuid, Duration.PERMANENT);
    }

    public void mutePlayer(Player player){
        mutePlayer(player.getUniqueId());
    }

    public void unmutePlayer(UUID uuid){
        mutedPlayers.remove(uuid);
    }

    public void unmutePlayer(Player player){
        unmutePlayer(player.getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(mutedPlayers.containsKey(uuid)) {
            long newMillis = mutedPlayers.get(uuid).millisEnd();
            boolean cancel;
            cancel = newMillis == -1 || newMillis < System.currentTimeMillis();
            if(!cancel) unmutePlayer(uuid);
            event.setCancelled(cancel);
        }
    }

}
