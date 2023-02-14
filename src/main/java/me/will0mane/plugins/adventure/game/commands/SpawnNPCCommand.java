package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.npcs.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SpawnNPCCommand extends CommandBuilder {

    public SpawnNPCCommand() {
        super("spawnnpc");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.spawnnpc"))) return CommandResponse.NO_PERMISSION;
        if(args.length < 1) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cPlease specify an npc!"));
        String npc = args[0];
        Optional<NPC> optionalNPC = Adventure.getRegistry().getNPCManager().getNPC(npc);
        if(optionalNPC.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis npc doesn't exist!"));
        NPC actualNPC = optionalNPC.get();
        actualNPC.createNPC(player.getLocation());
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aSuccess!"));
    }
}
