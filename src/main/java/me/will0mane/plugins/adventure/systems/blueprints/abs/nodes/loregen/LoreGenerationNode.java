package me.will0mane.plugins.adventure.systems.blueprints.abs.nodes.loregen;

import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LoreGenerationNode extends BlueprintNode {

    private final AdventureItem item;

    public LoreGenerationNode(AdventureItem item){
        this.item = item;
    }

    @Override
    public List<?> inputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<?> outputVars() {
        return Collections.emptyList();
    }

    @Override
    public List<String> executePin(Object... objects) {
        List<String> generatedLore = new ArrayList<>();
        if(!item.getDescription().isEmpty()) {
            generatedLore.add("");
            generatedLore.addAll(item.getDescription());
            generatedLore.add("");
        }

        if(!item.getDescription().isEmpty()){
            if(item.getDescription().size() < 5){
                for (ItemAbility<?> ability : item.getAbilities()) {
                    generatedLore.add("");
                    generatedLore.add("&6&l" + ability.activationMethodName() + " " + ability.getName().toUpperCase(Locale.ROOT) + ":");
                    generatedLore.addAll(ability.getDescription());
                }
            }else {
                generatedLore.add("");
                generatedLore.add("&e&oMore than 5 abilities!");
            }
        }
        return generatedLore;
    }
}
