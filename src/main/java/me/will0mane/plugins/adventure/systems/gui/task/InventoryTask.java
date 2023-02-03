package me.will0mane.plugins.adventure.systems.gui.task;

import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InventoryTask extends BukkitRunnable {

    private UUID uuid;
    private AdventureGUI gui;

    public InventoryTask(UUID uuid, AdventureGUI gui){
        this.uuid = uuid;
        this.gui = gui;
    }

    @Override
    public void run() {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) {
            cancel();
            return;
        }
        gui.onUpdate(player, gui.getContents());
    }
}
