package me.will0mane.plugins.adventure.systems.exceptions.adventureitem.abs;

import me.will0mane.plugins.adventure.systems.exceptions.adventureitem.ItemException;

public class NotHeadItemException extends ItemException {

    public NotHeadItemException(String varArgs) {
        super("Cannot set the head data of a Non-Head Item! Please initialize it with PLAYER_HEAD before! (" + varArgs + ")");
    }
}
