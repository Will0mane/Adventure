package me.will0mane.plugins.adventure.systems.blocks;

import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.morepersistentdatatypes.DataType;
import lombok.Getter;
import me.will0mane.plugins.adventure.Adventure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public abstract class AdventureBlock implements Listener {

    private static final NamespacedKey adventureKey = Adventure.getKey("adventureKey");

    @Getter
    private Material material;
    @Getter
    private final PersistentDataContainer dataContainer;
    @Getter
    private final Location location;
    @Getter
    private final UUID uuid = UUID.randomUUID();

    protected AdventureBlock(Material material, Location location){
        this.material = material;
        this.dataContainer = new CustomBlockData(location.getBlock(), Adventure.getInstance());
        this.location = location;
        location.getBlock().setType(material);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        CustomBlockData customBlockData = new CustomBlockData(Objects.requireNonNull(e.getClickedBlock()), Adventure.getInstance());
        if(!customBlockData.has(adventureKey, DataType.UUID)) return;
        UUID uid = customBlockData.get(adventureKey, DataType.UUID);
        if(uuid == uid) onClick(e.getPlayer(), e);
    }

    public abstract void onClick(Player player, PlayerInteractEvent e);

    public void setMaterial(Material material) {
        this.material = material;
        location.getBlock().setType(material);
    }

    public boolean hasKey(NamespacedKey namespacedKey, PersistentDataType<?,?> dataType){
        return dataContainer.has(namespacedKey, dataType);
    }

    @SuppressWarnings("unused")
    public Object getData(NamespacedKey namespacedKey, PersistentDataType<?,?> dataType, Object defaultObj){
        if(!hasKey(namespacedKey, dataType)) return defaultObj;
        return dataContainer.get(namespacedKey, dataType);
    }
}
