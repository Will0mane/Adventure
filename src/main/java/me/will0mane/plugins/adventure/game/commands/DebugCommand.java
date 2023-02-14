package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.debug.DebugHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand extends CommandBuilder {

    public DebugCommand() {
        super("debug");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args)  {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.debug"))) return CommandResponse.NO_PERMISSION;
        boolean using = DebugHandler.toggleDebug(player.getUniqueId());
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender(using ? "&aNow debugging!" : "&cNo longer debugging!"));
    }

}
