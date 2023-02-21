package me.will0mane.plugins.adventure.game.commands.pet_menu;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetMenuCommand extends CommandBuilder {

    private static final PetMenuGUI petMenuGUI = new PetMenuGUI();

    public PetMenuCommand() {
        super("pets");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args)  {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.petmenu"))) return CommandResponse.NO_PERMISSION;
        petMenuGUI.open(player);
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aOpening pets!"));
    }
}
