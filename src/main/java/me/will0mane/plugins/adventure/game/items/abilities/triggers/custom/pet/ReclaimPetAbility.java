package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.InteractAbility;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.AdventureStatManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ReclaimPetAbility extends ItemAbility<InteractAbility> {

    private static final String PDC_KEY = "petData";

    public ReclaimPetAbility() {
        super("reclaim_pet");
    }

    @Override
    public void trigger(InteractAbility data) {
        Player player = data.getEvent().getPlayer();
        UUID uuid = player.getUniqueId();
        if(Adventure.getRegistry().getSessionsHandler().get(uuid).isEmpty()) return;
        List<AdventurePet> curPets = AdventurePet.loadPets(uuid);

        Optional<AdventureItem> optionalItem = AdventureItem.getItem(data.getEvent().getItem());

        if(optionalItem.isEmpty()) return;

        AdventureItem item = optionalItem.get();

        String petData = item.get(PDC_KEY);

        if(petData == null) return;

        curPets.add(new AdventurePet(petData, uuid));
        AdventurePet.loadedPets.put(uuid, curPets);

        Adventure.getRegistry().getAdventureStatManager().saveAll(uuid);

        Objects.requireNonNull(player.getEquipment()).setItemInMainHand(new ItemStack(Material.AIR));
        ChatUtils.sendMessageTranslated(player, "&aAdded your pet!");
        SoundUtils.playSoundYaml("ENTITY_PLAYER_LEVELUP,1,1", player);
    }

    @Override
    public String getName() {
        return "CLAIM PET";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "&7Claims the pet, adding it",
                "&7to the ones you already own!"
        );
    }

    @Override
    public String activationMethodName() {
        return "ON CLICK";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.CLAIM_PET;
    }
}
