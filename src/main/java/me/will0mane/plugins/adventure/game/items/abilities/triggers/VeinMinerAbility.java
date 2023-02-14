package me.will0mane.plugins.adventure.game.items.abilities.triggers;

import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.BlockActionAbility;
import me.will0mane.plugins.adventure.systems.shapes.ShapesUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class VeinMinerAbility extends ItemAbility<BlockActionAbility> {

    public VeinMinerAbility() {
        super("vein_miner");
    }

    @Override
    public void trigger(BlockActionAbility data) {
        BlockEvent cause = data.getEvent();
        if(!(cause instanceof BlockBreakEvent event)) return;
        Block block = event.getBlock();
        ShapesUtils.getSphere(block.getLocation(), 5, true).forEach(relative -> {
            if(event.isDropItems()) relative.getDrops(event.getPlayer().getItemInUse()).forEach(itemStack ->
                    Objects.requireNonNull(relative.getLocation().getWorld()).dropItem(relative.getLocation(), itemStack));
            relative.setType(Material.AIR);
            Bukkit.broadcastMessage("block");
        });
    }

    @Override
    public String getName() {
        return "VEIN MINER";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("&7Mines all the", "&7near blocks!");
    }

    @Override
    public String activationMethodName() {
        return "ON BLOCK BREAK";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.VEIN_MINER;
    }
}
