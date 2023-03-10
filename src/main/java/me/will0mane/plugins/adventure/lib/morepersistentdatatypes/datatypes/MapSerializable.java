package me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes;

import lombok.SneakyThrows;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public record MapSerializable(
        Map<String, Object> map) implements ConfigurationSerializable {

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return map;
    }

    @SneakyThrows
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        map.forEach((s, o) -> builder.append("\n").append("&7- &b").append(s).append(": &a").append(o.toString()));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(outputStream);
        bukkitObjectOutputStream.writeObject(serialize());
        byte[] bytes = outputStream.toByteArray();
        builder.append("\n\n").append("&aData Size: ").append(bytes.length).append(" bytes");
        return builder.toString();
    }
}
