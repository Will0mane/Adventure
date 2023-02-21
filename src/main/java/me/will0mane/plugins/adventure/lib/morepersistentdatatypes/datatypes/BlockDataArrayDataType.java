
package me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes;

import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BlockDataArrayDataType implements PersistentDataType<byte[], BlockData[]> {
    @NotNull
    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @NotNull
    @Override
    public Class<BlockData[]> getComplexType() {
        return BlockData[].class;
    }

    @Override
    public byte [] toPrimitive(final BlockData [] blockData, final @NotNull PersistentDataAdapterContext context) {
        return DataType.STRING_ARRAY.toPrimitive(Arrays.stream(blockData).map(BlockData::getAsString).toArray(String[]::new),context);
    }

    @Override
    public BlockData [] fromPrimitive(final byte [] bytes, final @NotNull PersistentDataAdapterContext context) {
        return Arrays.stream(DataType.STRING_ARRAY.fromPrimitive(bytes, context)).map(Bukkit::createBlockData).toArray(BlockData[]::new);
    }
}