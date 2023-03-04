package me.will0mane.plugins.adventure.systems.items.blueprints.nodes;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.blueprints.nodes.BlueprintNode;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.abilities.ItemAbility;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import me.will0mane.plugins.adventure.systems.stats.types.AmountStatistic;

import java.util.*;

public class LoreGenerationNode extends BlueprintNode {

    private final AdventureItem item;

    public LoreGenerationNode(AdventureItem item) {
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
        Map<String, Double> itemStats = item.getStats();
        if(!itemStats.isEmpty()){
            generatedLore.add("");
            itemStats.forEach((s, d) -> {
                Optional<AdventureStat<?>> amountStatistic = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(s);
                if(amountStatistic.isPresent()){
                    AmountStatistic<Double> stat = (AmountStatistic<Double>) amountStatistic.get();
                    generatedLore.add("&7[" + stat.symbol() + "&7] " + stat.inGameName() + ": " + (d < 0 ? "&c" : "&b") + d);
                }
            });
        }
        if (!item.getDescription().isEmpty()) {
            generatedLore.add("");
            generatedLore.addAll(item.getDescription());
            generatedLore.add("");
        }

        if (!item.getDescription().isEmpty() && item.getDescription().size() < 5) {
            if (item.getAbilities().size() > 5) {
                generatedLore.add("");
                generatedLore.add("&e&oMore than 5 abilities!");
            } else {
                for (ItemAbility<?> ability : item.getAbilities()) {
                    generatedLore.add("");
                    generatedLore.add("&6&l" + ability.activationMethodName() + " " + ability.getName().toUpperCase(Locale.ROOT) + ":");
                    generatedLore.addAll(ability.getDescription());
                }
            }
        }
        return ChatUtils.translateAList(generatedLore);
    }
}
