package me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes.collections;

import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

import static me.will0mane.plugins.adventure.lib.morepersistentdatatypes.NamespacedKeyUtils.getKeyKey;
import static me.will0mane.plugins.adventure.lib.morepersistentdatatypes.NamespacedKeyUtils.getValueKey;

public class MapDataType<M extends Map<K, V>, K, V> implements PersistentDataType<PersistentDataContainer, M> {

    private static final String E_KEY_MUST_NOT_BE_NULL = "Maps stored in a PersistentDataContainer must not contain any null keys.";
    private static final String E_NOT_A_MAP = "Not a map.";
    private static final NamespacedKey KEY_SIZE = getKeyKey("s");


    private final Class<M> mapClazz;
    private final Supplier<? extends M> mapFactory;
    private final PersistentDataType<?, K> keyDataType;
    private final PersistentDataType<?, V> valueDataType;

    public MapDataType(@NotNull final Supplier<? extends M> factory,
                       @NotNull final PersistentDataType<?, K> keyDataType,
                       @NotNull final PersistentDataType<?, V> valueDataType) {
        this.mapFactory = factory;
        //noinspection unchecked
        this.mapClazz = (Class<M>) factory.get().getClass();
        this.keyDataType = keyDataType;
        this.valueDataType = valueDataType;
    }

    public static Builder<Map<Object,Object>,Object,Object> builder() {
        return new Builder<>();
    }

    @NotNull
    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @NotNull
    @Override
    public Class<M> getComplexType() {
        return mapClazz;
    }

    @NotNull
    @Override
    public PersistentDataContainer toPrimitive(@NotNull final M map, @NotNull final PersistentDataAdapterContext context) {
        final PersistentDataContainer pdc = context.newPersistentDataContainer();
        int index = 0;
        final int size = map.size();
        pdc.set(KEY_SIZE, DataType.INTEGER, size);
        for (final K key : map.keySet()) {
            if (key == null) {
                throw new IllegalArgumentException(E_KEY_MUST_NOT_BE_NULL);
            }
            final V value = map.get(key);
            if (value != null) {
                pdc.set(getValueKey(index), valueDataType, value);
            }
            pdc.set(getKeyKey(index++), keyDataType, key);
        }
        return pdc;
    }

    @NotNull
    @Override
    public M fromPrimitive(@NotNull final PersistentDataContainer pdc, @NotNull final PersistentDataAdapterContext context) {
        final M map = mapFactory.get();
        final Integer size = pdc.get(KEY_SIZE, DataType.INTEGER);
        if (size == null) {
            throw new IllegalArgumentException(E_NOT_A_MAP);
        }
        for (int i = 0; i < size; i++) {
            final K key = pdc.get(getKeyKey(i), keyDataType);
            map.put(key, pdc.get(getValueKey(i), valueDataType));
        }
        return map;
    }

    public static class Builder<M extends Map<K, V>,K,V> {
        private PersistentDataType<?, K> keyDataType;
        private PersistentDataType<?, V> valueDataType;

        @Contract("_ -> this")
        public <K1 extends K> Builder<?,K1,V> keyDataType(@NotNull final PersistentDataType<?, K1> keyDataType) {
            this.keyDataType = (PersistentDataType<?, K>) keyDataType;
            return (Builder<?, K1, V>) this;
        }
        @Contract("_ -> this")
        public <V1 extends V> Builder<?,K,V1> valueDataType(@NotNull final PersistentDataType<?, V1> valueDataType) {
            this.valueDataType = (PersistentDataType<?, V>) valueDataType;
            return (Builder<?, K, V1>) this;
        }
        @Contract("_ -> new")
        public <M1 extends M> MapDataType<M1,K,V> build(@NotNull final Supplier<? extends M1> mapFactory) {
            return new MapDataType<>(mapFactory, this.keyDataType, this.valueDataType);
        }
    }
}