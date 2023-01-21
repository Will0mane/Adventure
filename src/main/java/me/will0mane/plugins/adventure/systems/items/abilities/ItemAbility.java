package me.will0mane.plugins.adventure.systems.items.abilities;

import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import net.minecraft.sounds.SoundEffects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public abstract class ItemAbility<T extends AbilityData> {

    protected final String id;

    public ItemAbility(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void trigger(T data){
    }

    public Type getType(){
        return getClass().getGenericSuperclass();
    }

    public abstract String getName();

    public abstract List<String> getDescription();

    public abstract String activationMethodName();

    public void triggerWithArgs(Class<?> data, Object[] arguments){
        try {
            trigger((T) data.getDeclaredConstructors()[0].newInstance(arguments));
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public abstract Abilities getEnum();
}
