package me.will0mane.plugins.adventure.systems.items.states;

import lombok.Setter;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;

import java.util.function.Consumer;

public class AdventureItemState {

    private final String stateID;
    @Setter
    private Consumer<AdventureItem> activate;

    public AdventureItemState(String stateID, Consumer<AdventureItem> activate){
        this.stateID = stateID;
        this.activate = activate;
    }

    public void activate(AdventureItem item) {
        activate.accept(item);
    }

    public String getStateID() {
        return stateID;
    }

}
