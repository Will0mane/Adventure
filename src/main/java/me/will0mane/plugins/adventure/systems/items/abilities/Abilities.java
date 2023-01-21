package me.will0mane.plugins.adventure.systems.items.abilities;

import me.will0mane.plugins.adventure.systems.items.abilities.triggers.ArrowTrigger;

public enum Abilities {

    ARROW(new ArrowTrigger());

    public ItemAbility<?> ability;

    Abilities(ItemAbility<?> ability){
        this.ability = ability;
    }

    public ItemAbility<?> getAbility(){
        return ability;
    }
}
