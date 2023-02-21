package me.will0mane.plugins.adventure.systems.gui;

import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.events.ClickEvent;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.gui.task.InventoryTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public abstract class AdventureGUI implements Listener {

    @Getter
    private final Builder builder;
    @Getter
    private final UUID invUUID;
    @Getter
    private Contents contents;
    @Getter
    private final List<UUID> viewers = new ArrayList<>();
    @Getter
    private final Map<UUID, Integer> taskMap = new HashMap<>();

    protected AdventureGUI(Builder builder) {
        this.builder = builder;
        this.invUUID = UUID.randomUUID();
        this.contents = new Contents(this);
        Bukkit.getPluginManager().registerEvents(this, Adventure.getInstance());
    }

    public abstract void onInit(Player player, Contents contents);

    public abstract void onUpdate(Player player, Contents contents);

    public abstract void onClose(Player player);

    public void open(Player player) {
        viewers.add(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(player, builder.getColumn() * builder.getRows(),
                ChatUtils.translate(builder.getTitle()));
        player.openInventory(inventory);
        onInit(player, contents);
        taskMap.put(player.getUniqueId(),
                new InventoryTask(player.getUniqueId(), this)
                        .runTaskTimer(Adventure.getInstance(), 0, 1)
                        .getTaskId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory() == e.getWhoClicked().getOpenInventory().getTopInventory() && viewers.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null) return;

            int row = e.getSlot() / 9;
            int column = e.getSlot() % 9;

            Optional<Item> optionalItem = contents.getItem(row, column);

            if (optionalItem.isEmpty()) return;

            Item item = optionalItem.get();

            ClickEvent clickEvent = new ClickEvent(item, e.isLeftClick(), e.isShiftClick());
            Bukkit.getPluginManager().callEvent(clickEvent);

            item.getEventConsumer().accept(clickEvent);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (e.getInventory() == e.getPlayer().getOpenInventory().getTopInventory()) {
            onClose(Bukkit.getPlayer(uuid));
            contents = new Contents(this);
            viewers.remove(uuid);
            if (taskMap.containsKey(uuid)) {
                int task = taskMap.get(uuid);
                Bukkit.getScheduler().cancelTask(task);
            }
        }
    }

}
