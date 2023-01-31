package me.will0mane.plugins.adventure.systems.items.abilities;


import me.will0mane.plugins.adventure.game.items.abilities.triggers.*;

public enum Abilities {

    ARROW(new ArrowTrigger()),
    FAST_BOW(new FastBowAbility()),
    SNIPER_HIT(new SniperHitAbility()),
    AIM(new AimAbility()),
    VEIN_MINER(new VeinMinerAbility()),
    ;

    private final ItemAbility<?> ability;

    Abilities(ItemAbility<?> ability){
        this.ability = ability;
    }

    public ItemAbility<?> getAbility(){
        return ability;
    }
}
