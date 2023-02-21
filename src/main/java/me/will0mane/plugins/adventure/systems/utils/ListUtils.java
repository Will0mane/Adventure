package me.will0mane.plugins.adventure.systems.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static List<String> mergeLists(List<String>... lists){
        List<String> returns = new ArrayList<>();
        for(List<String> list : lists){
            returns.addAll(list);
        }
        return returns;
    }

}
