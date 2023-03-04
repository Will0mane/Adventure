package me.will0mane.plugins.adventure.game.commands.equipment;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.clicks.ClickInventoryType;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.builder.ItemBuilder;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.profile.AdventureProfile;
import me.will0mane.plugins.adventure.systems.profile.equipment.AdventureEquipment;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EquipmentGUI extends AdventureGUI {

    protected static final Item NOTHING_HERE = Item.empty(new ItemBuilder(Material.BARRIER).rename("&cNothing here!").build());
    protected static final int COLUMN = 4;
    protected EquipmentGUI() {
        super(new Builder().setTitle("&7Equipment").setSize(9,6));
    }

    @Override
    public void onInit(Player player, Contents contents, Object... objects) {
        allowClick(ClickInventoryType.OWN_INVENTORY);
        contents.fill(Item.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        UUID uuid = player.getUniqueId();
        AdventureProfile profile = AdventureProfile.getProfile(uuid);
        AdventureEquipment equipment = profile.getEquipment();

        if(equipment.getHeadStack() != null){
            contents.set(1, COLUMN, Item.empty(equipment.getHeadStack().buildItem()));
        }else {
            contents.set(1, COLUMN, NOTHING_HERE);
        }
        if(equipment.getChestStack() != null){
            contents.set(2, COLUMN, Item.empty(equipment.getChestStack().buildItem()));
        }else {
            contents.set(2, COLUMN, NOTHING_HERE);
        }
        if(equipment.getBeltStack() != null){
            contents.set(3, COLUMN, Item.empty(equipment.getBeltStack().buildItem()));
        }else {
            contents.set(3, COLUMN, NOTHING_HERE);
        }
        if(equipment.getFeetStack() != null){
            contents.set(4, COLUMN, Item.empty(equipment.getFeetStack().buildItem()));
        }else {
            contents.set(4, COLUMN, NOTHING_HERE);
        }
    }

    @Override
    public void onClickAllowed(InventoryClickEvent e, Object... objects){
        Player player = (Player) e.getWhoClicked();
        UUID uuid = e.getWhoClicked().getUniqueId();
        ItemStack itemStack = e.getCurrentItem();

        if(itemStack == null) {
            e.setCancelled(true);
            return;
        }
        Optional<AdventureItem> item = AdventureItem.getItem(itemStack);
        if(item.isEmpty()) {
            e.setCancelled(true);
            return;
        }

        AdventureItem adventureItem = item.get();
        Map<String, Object> data = adventureItem.getData();
        if(!data.containsKey("EQUIPMENT_ITEM")) {
            e.setCancelled(true);
            return;
        }
        String equipmentPiece = adventureItem.get("EQUIPMENT_PIECE");
        AdventureProfile profile = AdventureProfile.getProfile(uuid);
        AdventureEquipment equipment = profile.getEquipment();
        AdventureItem old;
        switch (equipmentPiece) {
            case "HEAD" -> {
                old = equipment.getHeadStack();
                equipment.setHeadStack(adventureItem);
            }
            case "CHEST" -> {
                old = equipment.getChestStack();
                equipment.setChestStack(adventureItem);
            }
            case "BELT" -> {
                old = equipment.getBeltStack();
                equipment.setBeltStack(adventureItem);
            }
            case "FEET" -> {
                old = equipment.getFeetStack();
                equipment.setFeetStack(adventureItem);
            }
            default -> {
                old = null;
            }
        }
        itemStack.setType(Material.AIR);
        if(old != null) {
            player.getInventory().addItem(old.buildItem());
        }

        Adventure.getRegistry().getAdventureStatManager().saveAll(uuid);

        close(player);
        open(player);
    }

    @Override
    public void onUpdate(Player player, Contents contents, Object... objects) {
    }

    @Override
    public void onClose(Player player, Object... objects) {
    }
}
