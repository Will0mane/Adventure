package me.will0mane.plugins.adventure.systems.gui;

import com.jeff_media.morepersistentdatatypes.DataType;
import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.events.ClickEvent;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.gui.task.InventoryTask;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.*;

public abstract class AdventureGUI implements Listener {

    private static final NamespacedKey partKey = Adventure.getKey("inventory");
    private static final NamespacedKey itemIdentificator = Adventure.getKey("invItemID");

    @Getter
    private Builder builder;
    @Getter
    private final UUID invUUID;
    @Getter
    private Contents contents;
    @Getter
    private final List<UUID> viewers = new ArrayList<>();
    @Getter
    private final Map<UUID, Integer> taskMap = new HashMap<>();

    protected AdventureGUI(Builder builder){
        this.builder = builder;
        this.invUUID = UUID.randomUUID();
    }

    public abstract void onInit(Player player, Contents contents);
    public abstract void onUpdate(Player player, Contents contents);
    public abstract void onClose(Player player);

    public void open(Player player){
        viewers.add(player.getUniqueId());
        contents = new Contents(this);
        Inventory inventory = Bukkit.createInventory(player, builder.getColumn() * builder.getRows(),
                ChatUtils.translate(builder.getTitle()));
        player.openInventory(inventory);
        onInit(player, contents);
        taskMap.put(player.getUniqueId(),
                new InventoryTask(player.getUniqueId(), this)
                        .runTaskTimer(Adventure.getInstance(), 0,1)
                        .getTaskId());
    }

    public boolean isInventoryItem(ItemStack itemStack){
        if(itemStack.getItemMeta() == null) return false;
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();
        if(!c.has(partKey, DataType.UUID)) return false;
        UUID uuid = c.get(partKey, DataType.UUID);
        return uuid == invUUID;
    }

    public Optional<Item> getItemFromStack(ItemStack itemStack){
        if(itemStack == null || itemStack.getItemMeta() == null) return Optional.empty();
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer c = meta.getPersistentDataContainer();
        if(!c.has(itemIdentificator, DataType.UUID)) return Optional.empty();
        UUID uuid = c.get(itemIdentificator, DataType.UUID);
        if(!contents.getItemMap().containsKey(uuid)) return Optional.empty();
        return Optional.ofNullable(contents.getItemMap().get(uuid));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack itemStack = e.getCurrentItem();
        if(itemStack == null || itemStack.getItemMeta() == null || !isInventoryItem(itemStack)) return;

        Optional<Item> optionalItem = getItemFromStack(itemStack);
        if(optionalItem.isEmpty()) return;

        Item item = optionalItem.get();
        if(item.getEventConsumer() == null) return;

        ClickEvent clickEvent = new ClickEvent(item, e.isLeftClick(), e.isShiftClick());
        Bukkit.getPluginManager().callEvent(clickEvent);
        item.getEventConsumer().accept(clickEvent);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(viewers.contains(uuid)){
            onClose(Bukkit.getPlayer(uuid));
            viewers.remove(uuid);
            if(taskMap.containsKey(uuid)){
                int task = taskMap.get(uuid);
                Bukkit.getScheduler().cancelTask(task);
            }
        }
    }

}
