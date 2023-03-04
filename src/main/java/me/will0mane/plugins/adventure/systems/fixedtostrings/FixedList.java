package me.will0mane.plugins.adventure.systems.fixedtostrings;

import java.util.List;

public abstract class FixedList<T> implements List<T> {

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        forEach(o -> builder.append(System.lineSeparator()).append("&7- &b").append(o.toString()));
        return builder.toString();
    }

}
