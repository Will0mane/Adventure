package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import me.will0mane.plugins.adventure.systems.pets.type.PetType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class CreatePetCommand extends CommandBuilder {

    public CreatePetCommand() {
        super("createpet");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.createpet"))) return CommandResponse.NO_PERMISSION;
        if(args.length < 2) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cPlease specify some arguments!"));
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis player isn't online!"));

        PetType petType = PetType.valueOf(args[1].toUpperCase());

        Optional<Class<?>> optionalBuilder = AdventureItemHandler.getBuilder("pet_item");
        if(optionalBuilder.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThis command is broken! Please contact a developer!"));
        Class<?> builder = optionalBuilder.get();
        AdventureItemBuilder obj = (AdventureItemBuilder) builder.getDeclaredConstructors()[0].newInstance(petType);
        obj.setup();
        player.getInventory().addItem(obj.getItemStack());
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aSuccess!"));
    }
}
