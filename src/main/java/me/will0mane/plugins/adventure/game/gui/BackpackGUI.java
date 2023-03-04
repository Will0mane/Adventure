package me.will0mane.plugins.adventure.game.gui;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.clicks.ClickInventoryType;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Optional;

public class BackpackGUI extends AdventureGUI {

    private final ItemStack[] itemStacks;
    private final Player player;

    public BackpackGUI(ItemStack[] contents, Player player, int[] backpackSizes) {
        super(new Builder().setSize(backpackSizes[1], backpackSizes[0]).setTitle("&7Backpack"));
        this.itemStacks = contents;
        this.player = player;
    }

    @Override
    public void onInit(Player player, Contents contents, Object... objects) {
            contents.setAsInventory(itemStacks);
            allowClick(ClickInventoryType.GUI);
            allowClick(ClickInventoryType.OWN_INVENTORY);
    }

    @Override
    public void onClickAllowed(InventoryClickEvent event, Object... objects){
        ItemStack itemStack = event.getCurrentItem();
        Optional<AdventureItem> optional = AdventureItem.getItem(itemStack);
        if(optional.isEmpty()) return;
        AdventureItem item = optional.get();
        if(item.get("backpack") != null && (boolean) item.get("backpack")){
            event.setCancelled(true);
            ChatUtils.sendMessageTranslated(event.getWhoClicked(), "&cYou can't move backpacks when one is already open!");
        }
    }

    @Override
    public void onUpdate(Player player, Contents contents, Object... objects) {
    }

    @Override
    public void onClose(Player player, Object... objects) {
        ItemStack itemStack = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
        Optional<AdventureItem> optional = AdventureItem.getItem(itemStack);
        if(optional.isEmpty()) return;
        AdventureItem item = optional.get();

        InventoryCloseEvent event = (InventoryCloseEvent) objects[1];
        ItemStack[] cont = event.getInventory().getContents();
        item.setKey("backpackInventory", cont);
    }
}
