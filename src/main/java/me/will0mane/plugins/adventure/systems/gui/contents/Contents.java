package me.will0mane.plugins.adventure.systems.gui.contents;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.gui.AdventureGUI;
import me.will0mane.plugins.adventure.systems.gui.item.Item;
import me.will0mane.plugins.adventure.systems.gui.slotpos.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Contents {

    @Getter
    private Item[][] contents;
    @Getter
    private final Map<UUID, Item> itemMap = new HashMap<>();
    @Getter
    private AdventureGUI gui;

    public Contents(AdventureGUI gui){
        this.gui = gui;
    }

    public Optional<Item> getItem(int row, int column){
        if(row >= contents.length)
            return Optional.empty();
        if(column >= contents[row].length)
            return Optional.empty();

        return Optional.ofNullable(contents[row][column]);
    }

    public Contents set(int row, int column, Item item) {
        if(row >= contents.length)
            return this;
        if(column >= contents[row].length)
            return this;

        contents[row][column] = item;

        update(row, column, item != null ? item.getItem() : null);
        return this;
    }

    public Contents fillEmpty(Item item){
        for(int x = 0; x < gui.getBuilder().getColumn(); x++){
            for(int y = 0; y < gui.getBuilder().getRows(); y++){
                if(contents[y][x] == null) continue;
                set(y,x, item);
            }
        }
        return this;
    }

    public Contents fill(Item item){
        for(int x = 0; x < gui.getBuilder().getColumn(); x++){
            for(int y = 0; y < gui.getBuilder().getRows(); y++){
                set(y,x, item);
            }
        }
        return this;
    }

    public Contents fillBorders(Item item){
        fillRect(0, 0, gui.getBuilder().getRows() - 1, gui.getBuilder().getColumn() - 1, item);
        return this;
    }

    public Contents fillRect(int fromRow, int fromColumn, int toRow, int toColumn, Item item) {
        for(int row = fromRow; row <= toRow; row++) {
            for(int column = fromColumn; column <= toColumn; column++) {
                if(row != fromRow && row != toRow && column != toColumn)
                    continue;

                set(row, column, item);
            }
        }
        return this;
    }

    public Contents fillColumn(int column, Item item) {
        for(int row = 0; row < contents.length; row++)
            set(row, column, item);

        return this;
    }

    public Contents fillRow(int row, Item item) {
        if(row >= contents.length)
            return this;

        for(int column = 0; column < contents[row].length; column++)
            set(row, column, item);

        return this;
    }

    public Contents add(Item item) {
        for(int row = 0; row < contents.length; row++) {
            for(int column = 0; column < contents[0].length; column++) {
                if(contents[row][column] == null) {
                    set(row, column, item);
                    return this;
                }
            }
        }
        return this;
    }

    public Optional<SlotPos> firstEmpty() {
        for (int row = 0; row < contents.length; row++) {
            for(int column = 0; column < contents[0].length; column++) {
                if(this.get(row, column).isEmpty())
                    return Optional.of(new SlotPos(row, column));
            }
        }

        return Optional.empty();
    }

    public Optional<Item> get(int row, int column) {
        if(row >= contents.length)
            return Optional.empty();
        if(column >= contents[row].length)
            return Optional.empty();

        return Optional.ofNullable(contents[row][column]);
    }

    private void update(int row, int column, ItemStack item) {
        for(UUID uuid : gui.getViewers()){
            Player currentPlayer = Bukkit.getPlayer(uuid);
            if(currentPlayer == null) return;
            Inventory topInventory = currentPlayer.getOpenInventory().getTopInventory();
            topInventory.setItem(gui.getBuilder().getColumn() * row + column, item);
        }
    }

}
