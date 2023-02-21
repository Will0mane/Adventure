package me.will0mane.plugins.adventure.systems.pets.rarity;

public enum AdventureRarity {

    COMMON("Common", "&7"),
    UNCOMMON("Uncommon", "&a"),
    RARE("Rare", "&b"),
    EPIC("Epic", "&d"),
    ULTRA_EPIC("Ultra-Epic", "&5"),
    LEGENDARY("Legendary", "&6"),
    MYTHIC("Mythic", "&e")
    ;

    private final String colorPrefix;
    private final String name;

    AdventureRarity(String name, String colorPrefix){
        this.name = name;
        this.colorPrefix = colorPrefix;
    }

    public String getName() {
        return name;
    }

    public String getColorPrefix() {
        return colorPrefix;
    }
}
