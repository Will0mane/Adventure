package me.will0mane.plugins.adventure.systems.gui;

import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.clicks.ClickInventoryType;
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
    private final List<ClickInventoryType> allowedClicks = new ArrayList<>();

    protected AdventureGUI(Builder builder) {
        this.builder = builder;
        this.invUUID = UUID.randomUUID();
        this.contents = new Contents(this);
        Bukkit.getPluginManager().registerEvents(this, Adventure.getInstance());
    }

    public abstract void onInit(Player player, Contents contents, Object... objects);

    public abstract void onUpdate(Player player, Contents contents, Object... objects);

    public abstract void onClose(Player player, Object... objects);

    public abstract void onClickAllowed(InventoryClickEvent event, Object... objects);

    public void close(Player player) {
        InventoryCloseEvent e = new InventoryCloseEvent(player.getOpenInventory());
        onInventoryClose(e);
        player.closeInventory();
    }

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

    public void allowClick(ClickInventoryType inventoryType) {
        this.allowedClicks.add(inventoryType);
    }

    public void denyClick(ClickInventoryType inventoryType) {
        this.allowedClicks.remove(inventoryType);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!viewers.contains(e.getWhoClicked().getUniqueId())) return;

        boolean cancel = false;
        boolean clickInventory = false;
        if (e.getClickedInventory() == e.getWhoClicked().getOpenInventory().getTopInventory()) {
            cancel = !allowedClicks.contains(ClickInventoryType.GUI);
            clickInventory = true;
        }
        if (e.getClickedInventory() == e.getWhoClicked().getInventory()) {
            cancel = !allowedClicks.contains(ClickInventoryType.OWN_INVENTORY);
        }
        if (cancel) {
            e.setCancelled(true);
        } else {
            onClickAllowed(e);
        }
        if(clickInventory){
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
        if (!viewers.contains(e.getPlayer().getUniqueId())) return;

        UUID uuid = e.getPlayer().getUniqueId();
        onClose(Bukkit.getPlayer(uuid), contents, e);
        viewers.remove(uuid);
        if (taskMap.containsKey(uuid)) {
            int task = taskMap.get(uuid);
            Bukkit.getScheduler().cancelTask(task);
        }
    }

}
