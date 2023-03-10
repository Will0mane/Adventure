package me.will0mane.plugins.adventure.systems.pets.rarity;

public enum PetRarity {

    COMMON("Common", "&7", 50,1),
    UNCOMMON("Uncommon", "&a", 75,1.5),
    RARE("Rare", "&b", 100,2),
    EPIC("Epic", "&d", 125,2.5),
    ULTRA_EPIC("Ultra-Epic", "&5", 150,3),
    LEGENDARY("Legendary", "&6", 250,3.5),
    MYTHIC("Mythic", "&e", 500, 4)
    ;

    private final String colorPrefix;
    private final String name;
    private final int petLevelThreshold;
    private final double weight;

    PetRarity(String name, String colorPrefix, int petLevelThreshold, double weight){
        this.name = name;
        this.colorPrefix = colorPrefix;
        this.petLevelThreshold = petLevelThreshold;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public PetRarity rankUp() {
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
