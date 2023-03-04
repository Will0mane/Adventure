package me.will0mane.plugins.adventure.systems.debug;

import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DebugHandler {

    private static final List<UUID> debugPlayers = new ArrayList<>();

    private DebugHandler(){}

    public static boolean toggleDebug(UUID uuid){
        if(debugPlayers.contains(uuid)) {
            debugPlayers.remove(uuid);
            return false;
        }
        debugPlayers.add(uuid);
        return true;
    }

    public static void sendDebug(String debug){
        debugPlayers.forEach(uuid -> ChatUtils.sendMessageTranslated(Bukkit.getPlayer(uuid), "&7[Debug] > &r" + debug));
    }

    public static void sendDebug(String... debug) {
        for(String string : debug){
            sendDebug(string);
        }
    }

}
