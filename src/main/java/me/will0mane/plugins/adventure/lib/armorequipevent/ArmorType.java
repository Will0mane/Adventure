package me.will0mane.plugins.adventure.lib.armorequipevent;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ArmorType{
    /**
     * Represents armor belonging to the helmet slot, e.g. helmets, skulls, and carved pumpkins.
     */
    HELMET(5, "Helmet"),
    /**
     * Represents armor belonging to the chestplate slot, e.g. chestplates and elytras.
     */
    CHESTPLATE(6, "Chestplate"),
    /**
     * Represents leggings.
     */
    LEGGINGS(7, "Leggings"),
    /**
     * Represents boots.
     */
    BOOTS(8, "Boots");

    private final int slot;
    private final String name;

    ArmorType(int slot, String name){
        this.slot = slot;
        this.name = name;
    }

    /**
     * Attempts to match the ArmorType for the specified ItemStack.
     *
     * @param itemStack The ItemStack to parse the type of.
     * @return The parsed ArmorType, or null if not found.
     */
    public static ArmorType matchType(ItemStack itemStack){
        if(ArmorListener.isEmpty(itemStack)) return null;
        Material type = itemStack.getType();
        String typeName = type.name();
        if(typeName.endsWith("_HELMET") || typeName.endsWith("_SKULL") || typeName.endsWith("_HEAD") || type == Material.CARVED_PUMPKIN) return HELMET;
        else if(typeName.endsWith("_CHESTPLATE") || typeName.equals("ELYTRA")) return CHESTPLATE;
        else if(typeName.endsWith("_LEGGINGS")) return LEGGINGS;
        else if(typeName.endsWith("_BOOTS")) return BOOTS;
        else return null;
    }

    /**
     * Returns the raw slot where this piece of armor usually gets equipped
     * @return Raw slot belonging to this piece of armor
     */
    public int getSlot(){
        return slot;
    }

    public String getName() {
        return name;
    }

    public Material getMaterialColorable() {
        return switch (this){
            case HELMET -> Material.PLAYER_HEAD;
            case CHESTPLATE -> Material.LEATHER_CHESTPLATE;
            case LEGGINGS -> Material.LEATHER_LEGGINGS;
            case BOOTS -> Material.LEATHER_BOOTS;
        };
    }
}
