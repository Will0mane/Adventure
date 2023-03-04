package me.will0mane.plugins.adventure.systems.listeners;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.DataType;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.data.PetInteractionAbility;
import me.will0mane.plugins.adventure.systems.listeners.abs.AdventureListener;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import me.will0mane.plugins.adventure.systems.player.AdventurePlayer;
import me.will0mane.plugins.adventure.systems.sessions.abs.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Optional;

public class PetInteractListener extends AdventureListener {

    private static final NamespacedKey PET_KEY = Adventure.getKey("petPiece");

    public PetInteractListener(JavaPlugin plugin){
        super(plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.hasItem()) return;
        AdventurePlayer player = AdventurePlayer.of(event.getPlayer());
        Optional<PlayerSession> optionalSession = player.getSession();

        if(optionalSession.isEmpty()) return;

        if(!AdventurePet.activePets.containsKey(player.getUUID())) return;

        ItemStack itemStack = event.getItem();
        if(itemStack.getItemMeta() == null) return;
        Optional<AdventureItem> item = AdventureItem.getItem(itemStack);
        if(item.isEmpty()) return;
        AdventureItem adventureItem = item.get();

        Map<String,Object> dataMap = adventureItem.getData();
        if(!dataMap.containsKey("petAbility")) return;

        AdventurePet pet = AdventurePet.activePets.get(event.getPlayer().getUniqueId());

        if(!player.getBukkitPlayer().hasLineOfSight(pet.getHead())) return;

        Entity entity = pet.getHead();
        PersistentDataContainer c = entity.getPersistentDataContainer();
        if(c.has(PET_KEY, DataType.ADVENTURE_PET) && Boolean.TRUE.equals(c.get(PET_KEY, DataType.ADVENTURE_PET))){
            PlayerInteractAtEntityEvent entityEvent = new PlayerInteractAtEntityEvent(player.getBukkitPlayer(), pet.getHead(), pet.getHead().getEyeLocation().toVector());
            Bukkit.getPluginManager().callEvent(entityEvent);
            adventureItem.inputAbility(PetInteractionAbility.class, entityEvent);
        }
    }
}
