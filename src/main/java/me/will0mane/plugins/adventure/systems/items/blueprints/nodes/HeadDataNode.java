package me.will0mane.plugins.adventure.systems.items.blueprints.nodes;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class HeadDataNode extends BlueprintNode {

    private final AdventureItem item;
    private final String data;

    public HeadDataNode(AdventureItem item, String data){
        this.item = item;
        this.data = data;
    }

    @Override
    public List<?> inputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<?> outputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<AdventureItem> executePin(Object... objects) {
        SkullMeta itemMeta = (SkullMeta) item.getOriginal().getItemMeta();
        if(itemMeta == null) return Collections.singletonList(item);

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField = null;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
            profileField.setAccessible(false);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        item.getOriginal().setItemMeta(itemMeta);
        return Collections.singletonList(item);
    }
}
