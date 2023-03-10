package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.stats.modes.StatMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class ItemStatsCommand extends CommandBuilder {

    public ItemStatsCommand() {
        super("itemstats");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if (!(sender.hasPermission("adventure.itemstats"))) return CommandResponse.NO_PERMISSION;
        Optional<AdventureItem> adventureItem = AdventureItem.getItem(Objects.requireNonNull(player.getEquipment()).getItemInMainHand());
        if (adventureItem.isEmpty())
            return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis item is not an adventure item!"));

        AdventureItem item = adventureItem.get();

        String id = args[1];
        if(args[0].equalsIgnoreCase("add")){
            StatMode type = StatMode.valueOf(args[2]);
            double value = Double.parseDouble(args[3]);
            item.setStatistic(id, value, type);
        }else {
            if(args[0].equalsIgnoreCase("remove")){
                item.removeStatistic(id);
            }
        }

        player.getEquipment().setItemInMainHand(item.buildItem());
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aSuccess!"));
    }
}