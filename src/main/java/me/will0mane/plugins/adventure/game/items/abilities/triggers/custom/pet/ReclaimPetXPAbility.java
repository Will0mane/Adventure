package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ReclaimPetXPAbility extends ItemAbility<InteractAbility> {

    public ReclaimPetXPAbility() {
        super("reclaim_pet");
    }

    @Override
    public void trigger(InteractAbility data) {
        Player player = data.getEvent().getPlayer();
        UUID uuid = player.getUniqueId();
        if(Adventure.getRegistry().getSessionsHandler().get(uuid).isEmpty()) return;
        if(!AdventurePet.activePets.containsKey(uuid)) {
            ChatUtils.sendMessageTranslated(player, "&cYou need an active pet in order to use this item!");
            return;
        }

        Optional<AdventureItem> optionalItem = AdventureItem.getItem(data.getEvent().getItem());

        if(optionalItem.isEmpty()) return;

        AdventureItem item = optionalItem.get();

        AdventurePet pet = AdventurePet.activePets.get(uuid);

        double xpAmount = item.get("xpOnClaim");
        pet.gainXP(xpAmount);

        Adventure.getRegistry().getAdventureStatManager().saveAll(uuid);

        EntityEquipment equipment = player.getEquipment();
        if(equipment == null) return;
        ItemStack itemStack = equipment.getItemInMainHand();
        if(itemStack.getAmount() == 1){
            equipment.setItemInMainHand(new ItemStack(Material.AIR));
        }else {
            itemStack.setAmount(itemStack.getAmount() - 1);
        }
        ChatUtils.sendMessageTranslated(player, "&aAdded xp to your pet!");
        SoundUtils.playSoundYaml("ENTITY_PLAYER_LEVELUP,1,1", player);
    }

    @Override
    public String getName() {
        return "CLAIM PET XP";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "&7Claims the pet xp, adding it",
                "&7to the pet you have activated!"
        );
    }

    @Override
    public String activationMethodName() {
        return "ON CLICK";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.CLAIM_PET_XP;
    }
}
