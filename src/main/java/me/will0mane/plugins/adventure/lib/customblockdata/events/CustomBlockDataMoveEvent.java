package me.will0mane.plugins.adventure.lib.customblockdata.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a block with CustomBlockData is moved by a piston to a new location.
 *
 * Blocks with protected CustomBlockData (see {@link me.will0mane.plugins.adventure.lib.customblockdata.CustomBlockData#isProtected()} will not trigger this event, however
 * it is possible that unprotected CustomBlockData will be moved to a destination block with protected CustomBlockData. You have
 * to cancel this event yourself to prevent this.
 */
public class CustomBlockDataMoveEvent extends CustomBlockDataEvent {

    private final @NotNull Block blockTo;

    public CustomBlockDataMoveEvent(@NotNull Plugin plugin, @NotNull Block blockFrom, @NotNull Block blockTo, @NotNull Event bukkitEvent) {
        super(plugin, blockFrom, bukkitEvent);
        this.blockTo = blockTo;
    }

    public @NotNull Block getBlockTo() {
        return blockTo;
    }

}