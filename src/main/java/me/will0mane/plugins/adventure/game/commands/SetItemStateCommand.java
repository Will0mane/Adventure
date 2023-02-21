package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Optional;

public class SetItemStateCommand extends CommandBuilder {

    public SetItemStateCommand() {
        super("setitemstate");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.setitemstate"))) return CommandResponse.NO_PERMISSION;
        Optional<AdventureItem> adventureItem = AdventureItem.getItem(Objects.requireNonNull(player.getEquipment()).getItemInMainHand());
        if(adventureItem.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis item is not an adventure item!"));

        AdventureItem item = adventureItem.get();
        String string = args[0];
        player.getEquipment().setItemInMainHand(item.buildItem());
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aSuccess!"));
    }
}
