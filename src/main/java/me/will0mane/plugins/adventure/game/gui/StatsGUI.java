package me.will0mane.plugins.adventure.game.gui;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.game.stats.mongodb.MongoStrength;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.AdventureStatManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class StatsGUI extends AdventureGUI {

    public StatsGUI() {
        super(new Builder().setSize(9,6).setTitle("&cStatistics"));
    }

    @Override
    public void onInit(Player player, Contents contents) {
        contents.fillBorders(Item.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));
        contents.set(1,1, Item.of(new ItemStack(Material.DIAMOND_SWORD), clickEvent -> {
            AdventureStatManager statManager = Adventure.getRegistry().getAdventureStatManager();
            Optional<AdventureStat<?>> stat = statManager.getRegisteredStatistic("strength");
            if(stat.isEmpty()) return;
            AdventureStat<?> adventureStat = stat.get();
            if(!(adventureStat instanceof MongoStrength mongoStrength)) return;
            ChatUtils.sendMessageTranslated(player, "&aYou have " + mongoStrength.get(player.getUniqueId()));
        }));
    }

    @Override
    public void onUpdate(Player player, Contents contents) {
    }

    @Override
    public void onClose(Player player) {
    }
}
