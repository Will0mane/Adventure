package me.will0mane.plugins.adventure.game.blocks;

import lombok.Getter;
import me.will0mane.plugins.adventure.game.gui.HackingGUI;
import me.will0mane.plugins.adventure.systems.blocks.AdventureBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ThreadLocalRandom;

public class HackingBlock extends AdventureBlock {

    @Getter
    private static final HackingGUI gui = new HackingGUI(ThreadLocalRandom.current().nextInt(1,5), o -> {});

    public HackingBlock(Location location) {
        super(Material.SEA_LANTERN, location);
    }

    @Override
    public void onClick(Player player, PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        gui.open(player);
    }
}
