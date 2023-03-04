package me.will0mane.plugins.adventure.systems.items.abilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public abstract class ItemAbility<T extends AbilityData> {

    protected final String id;
    protected final UUID uuid;

    protected ItemAbility(String id){
        this.id = id;
        this.uuid = UUID.randomUUID();
    }

    public String getId() {
        return id;
    }

    public abstract void trigger(T data);

    public Type getType(){
        return getClass().getGenericSuperclass();
    }

    public abstract String getName();

    public abstract List<String> getDescription();

    public abstract String activationMethodName();

    @SuppressWarnings("unchecked")
    public void triggerWithArgs(Class<?> data, Object[] arguments){
        try {
            trigger((T) data.getDeclaredConstructors()[0].newInstance(arguments));
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public abstract Abilities getEnum();
}
