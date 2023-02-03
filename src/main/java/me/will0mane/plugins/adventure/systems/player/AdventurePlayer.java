package me.will0mane.plugins.adventure.systems.player;

import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdventurePlayer {

    private static final Map<UUID, AdventurePlayer> playerMap = new HashMap<>();

    public static AdventurePlayer of(UUID uuid){
        playerMap.computeIfAbsent(uuid, uid -> new AdventurePlayer(uuid));
        return playerMap.get(uuid);
    }

    public static AdventurePlayer of(Player player){
        return of(player.getUniqueId());
    }

    //Class
    private final UUID uuid;

    public AdventurePlayer(UUID uuid){
        this.uuid = uuid;
    }

    public Player getBukkitPlayer(){
        return Bukkit.getPlayer(getUUID());
    }

    public UUID getUUID() {
        return uuid;
    }

    public void sendMessage(String... messages){
        ChatUtils.sendMessageTranslated(getBukkitPlayer(), messages);
    }

    public void sendTitle(String title, String subtitle, int in, int stay, int out){
        ChatUtils.sendTranslatedTitle(getBukkitPlayer(), title, subtitle, in, stay, out);
    }

    public void sendActionBar(String message){
        ChatUtils.sendTranslatedActionBar(getBukkitPlayer(), message);
    }
}
