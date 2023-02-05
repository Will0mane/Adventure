package me.will0mane.plugins.adventure.systems.hologram.click;

import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.function.Consumer;

public class HologramClickEvent extends AdventureListener {

    @Getter
    private final int line;
    @Getter
    private final HologramClickManager hologram;
    @Getter
    private final Consumer<Player> clickConsumer;

    public HologramClickEvent(int line, HologramClickManager hologram, Consumer<Player> clickConsumer){
        super(Adventure.getInstance());
        this.line = line;
        this.hologram = hologram;
        this.clickConsumer = clickConsumer;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        Slime slime = hologram.getClickEntity(line);
        if(slime == null) return;
        if(slime.getUniqueId() != event.getRightClicked().getUniqueId()) return;
        clickConsumer.accept(event.getPlayer());
    }

}
