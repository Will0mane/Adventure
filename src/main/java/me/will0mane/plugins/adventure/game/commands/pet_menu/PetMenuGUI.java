package me.will0mane.plugins.adventure.game.commands.pet_menu;

import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.builder.Builder;
import me.will0mane.plugins.adventure.systems.gui.contents.Contents;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.pets.AdventurePet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PetMenuGUI extends AdventureGUI {

    protected PetMenuGUI() {
        super(new Builder().setTitle("&7Pets").setSize(9,6));
    }

    @Override
    public void onInit(Player player, Contents contents) {
        contents.fillBorders(Item.empty(new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        UUID uuid = player.getUniqueId();
        AdventurePet.loadPets(uuid);

        int row = 1;
        int column = 1;
        for (AdventurePet adventurePet : AdventurePet.loadedPets.get(uuid)) {
            contents.set(row, column, Item.of(adventurePet.toItemStack(), clickEvent -> {
                if(clickEvent.isLeftClick()){
                    AdventurePet.equipPet(uuid, adventurePet);
                    return;
                }
                //TODO: To item
                if(clickEvent.isRightClick() && !clickEvent.isShifting()){
                    AdventurePet.disequipPet(uuid);
                }
            }));
            if((column + 1) == 8){
                column = 0;
                row++;
            }
            column++;
        }
    }

    @Override
    public void onUpdate(Player player, Contents contents) {
    }

    @Override
    public void onClose(Player player) {
    }
}
