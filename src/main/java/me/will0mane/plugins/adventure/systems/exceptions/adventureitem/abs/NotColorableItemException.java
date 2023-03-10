package me.will0mane.plugins.adventure.systems.exceptions.adventureitem.abs;

import me.will0mane.plugins.adventure.systems.exceptions.adventureitem.ItemException;

public class NotColorableItemException extends ItemException {

    public NotColorableItemException(String varArgs) {
        super("Cannot set the color of a Non-Colorable Item! Please initialize it with a LEATHER material before! (" + varArgs + ")");
    }
}
