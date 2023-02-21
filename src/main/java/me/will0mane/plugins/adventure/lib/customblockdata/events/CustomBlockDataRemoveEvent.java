package me.will0mane.plugins.adventure.lib.customblockdata.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Called when a block's CustomBlockData is about to be removed because the block was broken, replaced, or has changed in other ways.
 */
public class CustomBlockDataRemoveEvent extends CustomBlockDataEvent {

    public CustomBlockDataRemoveEvent(@NotNull Plugin plugin, @NotNull Block block, @Nullable Event bukkitEvent) {
        super(plugin, block, bukkitEvent);
    }

}