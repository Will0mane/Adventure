package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.game.blueprints.PlayerInventoryClearBlueprint;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand extends CommandBuilder {

    public ClearInventoryCommand() {
        super("clearinv");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws Exception {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!sender.hasPermission("adventure.clearinv")) return CommandResponse.NO_PERMISSION;
        PlayerInventoryClearBlueprint blueprint = new PlayerInventoryClearBlueprint(player.getUniqueId());
        blueprint.execPin();
        return new CommandResponse(commandBuilder -> {
            commandBuilder.sendMessageToSender("&aClearing!");
        });
    }
}
