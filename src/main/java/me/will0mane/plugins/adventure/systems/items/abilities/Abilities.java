package me.will0mane.plugins.adventure.systems.items.abilities;


import me.will0mane.plugins.adventure.game.items.abilities.triggers.*;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.PowerfulArrowTrigger;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.ReclaimPetAbility;
import me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.YellowStoneAbility;

public enum Abilities {

    ARROW(new ArrowTrigger()),
    POWERFUL_ARROW(new PowerfulArrowTrigger()),
    FAST_BOW(new FastBowAbility()),
    SNIPER_HIT(new SniperHitAbility()),
    AIM(new AimAbility()),
    VEIN_MINER(new VeinMinerAbility()),
    YELLOW_STONE(new YellowStoneAbility()),
    CLAIM_PET(new ReclaimPetAbility()),
    ;

    private final ItemAbility<?> ability;

    Abilities(ItemAbility<?> ability){
        this.ability = ability;
    }

    public ItemAbility<?> getAbility(){
        return ability;
    }
}
