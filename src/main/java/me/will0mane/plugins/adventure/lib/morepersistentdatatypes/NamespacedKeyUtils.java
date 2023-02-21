package me.will0mane.plugins.adventure.lib.morepersistentdatatypes;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * For internal use only.
 */
@UtilityClass
public class NamespacedKeyUtils {

    private static final Map<String, NamespacedKey> KEY_KEYS = new HashMap<>();
    private static final Map<String, NamespacedKey> VALUE_KEYS = new HashMap<>();

    public static final NamespacedKey KEY_NULL = getValueKey("n");

    static {
        // Caching the first 100 keys. I think that's reasonable for most use cases
        IntStream.range(0,100).forEach(number -> {
            getValueKey(number);
            getKeyKey(number);
        });
    }

    public static NamespacedKey getKeyKey(final int name) {
        return getKeyKey(String.valueOf(name));
    }

    public static NamespacedKey getKeyKey(final String name) {
        return KEY_KEYS.computeIfAbsent(name, __ -> NamespacedKey.fromString("k:" + name));
    }

    public static NamespacedKey getValueKey(final int name) {
        return getValueKey(String.valueOf(name));
    }

    public static NamespacedKey getValueKey(final String name) {
        return VALUE_KEYS.computeIfAbsent(name, __ -> NamespacedKey.fromString("v:" + name));
    }
}