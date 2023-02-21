package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.stats.AdventureStat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Optional;

public class StatisticCommand extends CommandBuilder {

    public StatisticCommand() {
        super("statistics");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.statistics"))) return CommandResponse.NO_PERMISSION;
        if(args.length < 2) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cPlease specify some arguments!"));

        if(args[0].equalsIgnoreCase("get")){
            return executeQuery(args);
        }
        if (args[0].equalsIgnoreCase("set")) {
            return executeSet(args);
        }
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis method doesn't exist!"));
    }

    private CommandResponse executeSet(String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis user doesn't exist!"));

        String statID = args[2].toLowerCase(Locale.ROOT);
        Optional<AdventureStat<?>> statOptional = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(statID);

        if(statOptional.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis statistic doesn't exist!"));
        AdventureStat<?> stat = statOptional.get();
        stat.setAsString(player.getUniqueId(), args[3]);
        Bukkit.broadcastMessage(args[3]);
        return new CommandResponse(commandBuilder ->
                commandBuilder.sendMessageToSender("&a" + player.getName() + "'s " + statID + " is now set to: " + stat.getAsString(player.getUniqueId())));
    }

    private CommandResponse executeQuery(String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis user doesn't exist!"));

        String statID = args[2].toLowerCase(Locale.ROOT);
        Optional<AdventureStat<?>> statOptional = Adventure.getRegistry().getAdventureStatManager().getRegisteredStatistic(statID);

        if(statOptional.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis statistic doesn't exist!"));
        AdventureStat<?> stat = statOptional.get();
        return new CommandResponse(commandBuilder ->
                commandBuilder.sendMessageToSender("&aHere is your statistic &7(&b" + statID + "&7)&a: &b" + stat.getAsString(player.getUniqueId())));
    }
}
