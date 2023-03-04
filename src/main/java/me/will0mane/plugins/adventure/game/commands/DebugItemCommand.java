package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.lib.morepersistentdatatypes.datatypes.MapSerializable;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DebugItemCommand extends CommandBuilder {

    public DebugItemCommand() {
        super("debugitem");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.debugitem"))) return CommandResponse.NO_PERMISSION;
        Optional<AdventureItem> optionalItem = AdventureItem.getItem(Objects.requireNonNull(player.getEquipment()).getItemInMainHand());
        if(optionalItem.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis item is not an adventure item! I can't read data from it!"));

        AdventureItem item = optionalItem.get();
        Map<String, Object> dataMap = item.getData();
        if(dataMap.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cSadly this item doesn't contain any data while it is an adventure item!"));

        ChatUtils.sendMessageTranslated(player, "&aFound Data:");
        ChatUtils.sendMessageTranslated(player, new MapSerializable(dataMap).toString());
        return CommandResponse.HANDLED;
    }
}
