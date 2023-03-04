package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.backpack;

import me.will0mane.plugins.adventure.game.gui.BackpackGUI;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackpackAbility extends ItemAbility<InteractAbility> {

    public BackpackAbility() {
        super("backpack");
    }

    @Override
    public void trigger(InteractAbility data) {
        PlayerInteractEvent event = data.getEvent();
        if(event.getAction() == Action.LEFT_CLICK_AIR ||
                event.getAction() == Action.LEFT_CLICK_BLOCK ||
                event.getAction() == Action.PHYSICAL) return;
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if(itemStack == null) return;

        Optional<AdventureItem> optionalItem = AdventureItem.getItem(itemStack);
        if(optionalItem.isEmpty()) return;
        AdventureItem item = optionalItem.get();

        Map<String,Object> dataMap = item.getData();
        if(!dataMap.containsKey("backpackInventory")) return;

        ItemStack[] itemStacks = (ItemStack[]) dataMap.get("backpackInventory");
        BackpackGUI gui = new BackpackGUI(itemStacks, player, (int[]) dataMap.get("backpackSize"));
        gui.open(player);
        SoundUtils.playSoundYaml("BLOCK_SHULKER_BOX_OPEN,1,0.5", player);
    }

    @Override
    public String getName() {
        return "OPEN BACKPACK";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "&7Opens the backpack."
        );
    }

    @Override
    public String activationMethodName() {
        return "ON INTERACT";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.BACKPACK;
    }
}
