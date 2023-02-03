package me.will0mane.plugins.adventure.game.gui;

import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.items.builder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class HackingGUI extends AdventureGUI {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public HackingGUI(int level, Consumer<Object> finishedHacking) {
        super(new Builder()
                .setTitle("%%gray%%Hacking - Level " + level)
                .setSize(9,6));
    }

    @Override
    public void onInit(Player player, Contents contents) {
        int[] ioPortLoc = new int[]{
                random.nextInt(1,6),
                random.nextInt(1,9)
        };
        contents.set(ioPortLoc[0], ioPortLoc[1], Item.empty(
                new ItemBuilder(Material.GREEN_STAINED_GLASS)
                        .rename("&7I/O Port")
                        .setLore(Arrays.asList(
                                "&7This is your spawn",
                                "&7you can't do anything",
                                "&7here."))
                        .build()));

        contents.fillBorders(Item.empty(
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .rename("&7Hacking")
                        .build()));
    }

    @Override
    public void onUpdate(Player player, Contents contents) {
    }

    @Override
    public void onClose(Player player) {
    }
}
