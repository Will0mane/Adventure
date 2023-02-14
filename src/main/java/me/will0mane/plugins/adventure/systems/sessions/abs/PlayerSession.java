package me.will0mane.plugins.adventure.systems.sessions.abs;

import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.sessions.Session;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerSession extends Session {

    @Getter
    public final Player player;

    public PlayerSession(UUID uuid){
        this(Bukkit.getPlayer(uuid));
    }

    public PlayerSession(Player player){
        this.player = player;
        validate();
    }

    @Override
    public void terminate() {
        Adventure.getRegistry().getModeration()
                .kickPlayer(player, "%%red%%Your session has been terminated!");
        invalidate();
    }

    @Override
    public void pause() {
        terminate();
    }

    @Override
    public void retry() {
        terminate();
    }
}
