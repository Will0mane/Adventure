package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.game.gui.StatsGUI;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand extends CommandBuilder {

    private static final StatsGUI statsGUI = new StatsGUI();

    public StatsCommand() {
        super("stats");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args)  {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.stats"))) return CommandResponse.NO_PERMISSION;
        statsGUI.open(player);
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aOpening stats!"));
    }
}
