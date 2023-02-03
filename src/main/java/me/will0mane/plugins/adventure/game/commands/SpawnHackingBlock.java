package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.game.blocks.HackingBlock;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnHackingBlock extends CommandBuilder {

    public SpawnHackingBlock() {
        super("shblock");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws Exception {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        Location location = player.getLocation();
        new HackingBlock(location);
        return CommandResponse.HANDLED;
    }
}
