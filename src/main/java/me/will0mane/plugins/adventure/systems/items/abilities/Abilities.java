package me.will0mane.plugins.adventure.systems.items.abilities;


import me.will0mane.plugins.adventure.game.items.abilities.triggers.*;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.*;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.backpack.BackpackAbility;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.PetRarityUpAbility;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.ReclaimPetAbility;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet.ReclaimPetXPAbility;

public enum Abilities {

    ARROW(new ArrowTrigger()),
    POWERFUL_ARROW(new PowerfulArrowTrigger()),
    FAST_BOW(new FastBowAbility()),
    SNIPER_HIT(new SniperHitAbility()),
    AIM(new AimAbility()),
    VEIN_MINER(new VeinMinerAbility()),
    YELLOW_STONE(new YellowStoneAbility()),
    CLAIM_PET(new ReclaimPetAbility()),
    CLAIM_PET_XP(new ReclaimPetXPAbility()),
    PET_RARITY_UP(new PetRarityUpAbility()),
    BACKPACK(new BackpackAbility()),
    ;

    private final ItemAbility<?> ability;

    Abilities(ItemAbility<?> ability){
        this.ability = ability;
    }

    public ItemAbility<?> getAbility(){
        return ability;
    }
}
