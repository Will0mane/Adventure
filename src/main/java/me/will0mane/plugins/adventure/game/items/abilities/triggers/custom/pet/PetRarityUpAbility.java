package me.will0mane.plugins.adventure.game.items.abilities.triggers.custom.pet;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.abilities.Abilities;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.items.abilities.data.PetInteractionAbility;
import me.will0mane.plugins.adventure.systems.particle.SoundUtils;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.pets.rarity.AdventureRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PetRarityUpAbility extends ItemAbility<PetInteractionAbility> {

    public PetRarityUpAbility() {
        super("reclaim_pet");
    }

    @Override
    public void trigger(PetInteractionAbility data) {
        Player player = data.getEvent().getPlayer();
        UUID uuid = player.getUniqueId();
        if(Adventure.getRegistry().getSessionsHandler().get(uuid).isEmpty()) return;
        if(!AdventurePet.activePets.containsKey(uuid)) {
            ChatUtils.sendMessageTranslated(player, "&cYou need an active pet in order to use this item!");
            return;
        }

        AdventurePet pet = AdventurePet.activePets.get(uuid);

        if(pet.getLevel() < pet.getRarity().getPetLevelThreshold()){
            ChatUtils.sendMessageTranslated(player, "&cThe pet must be at max level in order to rankup!");
            return;
        }

        AdventureRarity rarity = pet.getRarity().rankUp();
        if(rarity == null){
            ChatUtils.sendMessageTranslated(player, "&cThe pet is already at maximum rank!");
        }else {
            pet.setRarity(rarity);
            double retainedXP = pet.getCurXP();
            pet.setCurXP(0);
            pet.setXpMax(100);
            pet.setLevel(0);
            pet.gainXP(retainedXP);
        }

        Adventure.getRegistry().getAdventureStatManager().saveAll(uuid);

        EntityEquipment equipment = player.getEquipment();
        if(equipment == null) return;
        ItemStack itemStack = equipment.getItemInMainHand();
        if(itemStack.getAmount() == 1){
            equipment.setItemInMainHand(new ItemStack(Material.AIR));
        }else {
            itemStack.setAmount(itemStack.getAmount() - 1);
        }
        ChatUtils.sendMessageTranslated(player, "&aYour pet ranked up!");
        SoundUtils.playSoundYaml("ENTITY_PLAYER_LEVELUP,1,1", player);
    }

    @Override
    public String getName() {
        return "RANKUP PET";
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(
                "&7Upgrades the pet rarity!",
                "&7Only available at max level!"
        );
    }

    @Override
    public String activationMethodName() {
        return "ON CLICK PET";
    }

    @Override
    public Abilities getEnum() {
        return Abilities.PET_RARITY_UP;
    }
}

