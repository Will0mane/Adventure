package me.will0mane.plugins.adventure.systems.profile.equipment;

import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class AdventureEquipment implements ConfigurationSerializable {

    private AdventureItem headStack;
    private AdventureItem chestStack;
    private AdventureItem beltStack;
    private AdventureItem feetStack;

    public AdventureEquipment(AdventureItem headStack, AdventureItem chestStack, AdventureItem beltStack, AdventureItem feetStack){
        this.headStack = headStack;
        this.chestStack = chestStack;
        this.beltStack = beltStack;
        this.feetStack = feetStack;
    }

    public AdventureEquipment(){
        this(null, null, null, null);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("head", headStack);
        data.put("chest", chestStack);
        data.put("belt", beltStack);
        data.put("feet", feetStack);
        return data;
    }

    public void setHeadStack(AdventureItem headStack) {
        this.headStack = headStack;
    }

    public AdventureItem getHeadStack() {
        return headStack;
    }

    public void setChestStack(AdventureItem chestStack) {
        this.chestStack = chestStack;
    }

    public AdventureItem getChestStack() {
        return chestStack;
    }

    public void setBeltStack(AdventureItem beltStack) {
        this.beltStack = beltStack;
    }

    public AdventureItem getBeltStack() {
        return beltStack;
    }

    public void setFeetStack(AdventureItem feetStack) {
        this.feetStack = feetStack;
    }

    public AdventureItem getFeetStack() {
        return feetStack;
    }
}
