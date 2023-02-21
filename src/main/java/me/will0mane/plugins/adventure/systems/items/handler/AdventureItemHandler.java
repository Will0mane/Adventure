package me.will0mane.plugins.adventure.systems.items.handler;

import me.will0mane.plugins.adventure.game.items.types.*;
import me.will0mane.plugins.adventure.game.items.types.custom.PowerfulBow;
import me.will0mane.plugins.adventure.game.items.types.custom.YellowStone;
import me.will0mane.plugins.adventure.game.items.types.custom.pet.PetItem;
import me.will0mane.plugins.adventure.game.items.types.custom.pet.PetMenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdventureItemHandler {

    private static final Map<String, Class<?>> items = new HashMap<>();

    public static void registerItem(String itemId, Class<?> builder){
        items.put(itemId, builder);
    }

    public static Optional<Class<?>> getBuilder(String id){
        if(!items.containsKey(id)) return Optional.empty();
        return Optional.of(items.get(id));
    }

    public static void unregisterItem(String id){
        items.remove(id);
    }

    public void registerDefaults() {
        registerItem("fast_bow", FastBow.class);
        registerItem("sniper", Sniper.class);
        registerItem("vein_miner", VeinMinerPickaxe.class);
        registerItem("powerful_bow", PowerfulBow.class);
        registerItem("yellowstone", YellowStone.class);
        registerItem("pet_item", PetItem.class);
        registerItem("pet_menu", PetMenuItem.class);
    }
}
