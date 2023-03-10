package me.will0mane.plugins.adventure.lib.armorequipevent;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;
import java.util.Optional;

class ArmorListener implements Listener {

    public ArmorListener() {
    }

    //Event Priority is highest because other plugins might cancel the events before we check
    // .
    @EventHandler
    public final void onClick(final InventoryClickEvent event) {
        boolean shift = false, numberkey = false;
        if (event.getAction() == InventoryAction.NOTHING) return;// Why does this get called if nothing happens??
        if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) {
            shift = true;
        }
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            numberkey = true;
        }
        if (event.getClick() == ClickType.SWAP_OFFHAND) {
            numberkey = true;
        }
        if (event.getSlotType() != InventoryType.SlotType.ARMOR && event.getSlotType() != InventoryType.SlotType.QUICKBAR && event.getSlotType() != InventoryType.SlotType.CONTAINER)
            return;
        if (event.getClickedInventory() != null && !event.getClickedInventory().getType().equals(InventoryType.PLAYER))
            return;
        if (!event.getInventory().getType().equals(InventoryType.CRAFTING) && !event.getInventory().getType().equals(InventoryType.PLAYER))
            return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        ArmorType newArmorType = ArmorType.matchType(shift ? event.getCurrentItem() : event.getCursor());
        if (!shift && newArmorType != null && event.getRawSlot() != newArmorType.getSlot()) {
            // Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
            return;
        }
        if (shift) {
            newArmorType = ArmorType.matchType(event.getCurrentItem());
            if (newArmorType != null) {
                boolean equipping = true;
                if (event.getRawSlot() == newArmorType.getSlot()) {
                    equipping = false;
                }
                if (newArmorType.equals(ArmorType.HELMET) && (equipping ? isEmpty(event.getWhoClicked().getInventory().getHelmet()) : !isEmpty(event.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping ? isEmpty(event.getWhoClicked().getInventory().getChestplate()) : !isEmpty(event.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals(ArmorType.LEGGINGS) && (equipping ? isEmpty(event.getWhoClicked().getInventory().getLeggings()) : !isEmpty(event.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals(ArmorType.BOOTS) && (equipping ? isEmpty(event.getWhoClicked().getInventory().getBoots()) : !isEmpty(event.getWhoClicked().getInventory().getBoots()))) {
                    ArmorEvent armorEquipEvent = new ArmorEvent((Player) event.getWhoClicked(), ArmorEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : event.getCurrentItem(), equipping ? event.getCurrentItem() : null);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = event.getCursor();
            ItemStack oldArmorPiece = event.getCurrentItem();
            if (numberkey) {
                if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {// Prevents shit in the 2by2 crafting
                    ItemStack hotbarItem = null;
                    if (event.getHotbarButton() != -1) {
                        hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton());
                    } else if (event.getHotbarButton() == -1 && event.getClickedInventory() instanceof PlayerInventory inventory) {
                        hotbarItem = inventory.getItem(EquipmentSlot.OFF_HAND);
                    }
                    if (!isEmpty(hotbarItem)) {
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = event.getClickedInventory().getItem(event.getSlot());
                    } else {
                        newArmorType = ArmorType.matchType(!isEmpty(event.getCurrentItem()) ? event.getCurrentItem() : event.getCursor());
                    }
                }
            } else {
                if (isEmpty(event.getCursor()) && !isEmpty(event.getCurrentItem())) {
                    newArmorType = ArmorType.matchType(event.getCurrentItem());
                }
            }
            if (newArmorType != null && event.getRawSlot() == newArmorType.getSlot()) {
                ArmorEvent.EquipMethod method = ArmorEvent.EquipMethod.PICK_DROP;
                if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey)
                    method = ArmorEvent.EquipMethod.HOTBAR_SWAP;
                ArmorEvent armorEquipEvent = new ArmorEvent((Player) event.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.useItemInHand().equals(Event.Result.DENY)) return;

        if (event.getAction() == Action.PHYSICAL) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ArmorType newArmorType = ArmorType.matchType(event.getItem());

            // Carved pumpkins cannot be equipped using right-click
            if (event.getItem() != null && event.getItem().getType() == Material.CARVED_PUMPKIN) return;

            if (newArmorType != null) {
                if (newArmorType.equals(ArmorType.HELMET)
                        && isEmpty(event.getPlayer().getInventory().getHelmet())
                        || newArmorType.equals(ArmorType.CHESTPLATE)
                        && isEmpty(event.getPlayer().getInventory().getChestplate())
                        || newArmorType.equals(ArmorType.LEGGINGS)
                        && isEmpty(event.getPlayer().getInventory().getLeggings())
                        || newArmorType.equals(ArmorType.BOOTS)
                        && isEmpty(event.getPlayer().getInventory().getBoots())) {
                    ArmorEvent armorEquipEvent = new ArmorEvent(event.getPlayer(), ArmorEvent.EquipMethod.HOTBAR, ArmorType.matchType(event.getItem()), null, event.getItem());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        event.setCancelled(true);
                        player.updateInventory();
                    }else {
                        EntityEquipment equipment = player.getEquipment();
                        Optional<AdventureItem> optional = AdventureItem.getItem(event.getItem());
                        boolean move = true;
                        if(optional.isPresent()){
                            Map<String,Object> data = optional.get().getData();
                            if(data.containsKey("armorMovable") && !((boolean) data.get("armorMovable"))){
                                move = false;
                            }
                        }
                        if(move){
                            event.setCancelled(true);
                            switch (newArmorType){
                                case HELMET -> equipment.setHelmet(event.getItem());
                                case CHESTPLATE -> equipment.setChestplate(event.getItem());
                                case LEGGINGS -> equipment.setLeggings(event.getItem());
                                case BOOTS -> equipment.setBoots(event.getItem());
                            }
                            equipment.setItemInMainHand(null);
                        }
                    }
                }
            }
        }
    }

    static boolean isEmpty(ItemStack item) {
        return (item == null || item.getType().isAir() || item.getAmount() == 0);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) return;
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)) {
            ArmorEvent armorEquipEvent = new ArmorEvent((Player) event.getWhoClicked(), ArmorEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        ArmorType type = ArmorType.matchType(event.getBrokenItem());
        if (type != null) {
            Player p = event.getPlayer();
            ArmorEvent armorEquipEvent = new ArmorEvent(p, ArmorEvent.EquipMethod.BROKE, type, event.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = event.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short) (i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (event.getKeepInventory()) return;
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (!isEmpty(i)) {
                Bukkit.getServer().getPluginManager().callEvent(new ArmorEvent(p, ArmorEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
                // No way to cancel a death event.
            }
        }
    }

}
