package me.will0mane.plugins.adventure.systems.pets.rarity;

public enum AdventureRarity {

    COMMON("Common", "&7", 50),
    UNCOMMON("Uncommon", "&a", 75),
    RARE("Rare", "&b", 100),
    EPIC("Epic", "&d", 125),
    ULTRA_EPIC("Ultra-Epic", "&5", 150),
    LEGENDARY("Legendary", "&6", 250),
    MYTHIC("Mythic", "&e", 500)
    ;

    private final String colorPrefix;
    private final String name;
    private final int petLevelThreshold;

    AdventureRarity(String name, String colorPrefix, int petLevelThreshold){
        this.name = name;
        this.colorPrefix = colorPrefix;
        this.petLevelThreshold = petLevelThreshold;
    }

    public String getName() {
        return name;
    }

    public String getColorPrefix() {
        return colorPrefix;
    }

    public int getPetLevelThreshold() {
        return petLevelThreshold;
    }

    public AdventureRarity rankUp() {
        return switch (this){
            case COMMON -> UNCOMMON;
            case UNCOMMON -> RARE;
            case RARE -> EPIC;
            case EPIC -> ULTRA_EPIC;
            case ULTRA_EPIC -> LEGENDARY;
            case LEGENDARY -> MYTHIC;
            case MYTHIC -> null;
        };
    }
}
