package me.will0mane.plugins.adventure.game.commands.equipment;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EquipmentCommand extends CommandBuilder {

    private static final EquipmentGUI equipmentGUI = new EquipmentGUI();

    public EquipmentCommand() {
        super("equipment");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args)  {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.equipment"))) return CommandResponse.NO_PERMISSION;
        equipmentGUI.open(player);
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aOpening equipment!"));
    }
}
