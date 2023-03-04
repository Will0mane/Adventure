package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.items.AdventureItem;
import me.will0mane.plugins.adventure.systems.items.builder.AdventureItemBuilder;
import me.will0mane.plugins.adventure.systems.items.handler.AdventureItemHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GiveItemCommand extends CommandBuilder {

    public GiveItemCommand() {
        super("giveitem");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.giveitem"))) return CommandResponse.NO_PERMISSION;
        if(args.length < 1) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cPlease specify an item!"));
        String item = args[0];
        Optional<Class<?>> optionalBuilder = AdventureItemHandler.getBuilder(item.toLowerCase());
        if(optionalBuilder.isEmpty()) return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&cThe item you specified does not exist!"));
        Class<?> builder = optionalBuilder.get();
        AdventureItemBuilder obj;
        if(args.length > 1){
            String[] initArgs = getInitArgs(args);
            obj = (AdventureItemBuilder) builder.getDeclaredConstructors()[0].newInstance(initArgs);
        }else {
            obj = (AdventureItemBuilder) builder.getDeclaredConstructors()[0].newInstance();
        }
        obj.setup();
        ItemStack itemStack = obj.getItemStack();
        AdventureItem adv = obj.getItem();
        AdventureItem.getItems().remove(adv.getUuid());
        player.getInventory().addItem(itemStack);
        return new CommandResponse(commandBuilder -> commandBuilder.sendMessageToSender("&aSuccess!"));
    }

    private String[] getInitArgs(String[] args) {
        int index = 0;
        List<String> objectList = new ArrayList<>();
        for(String string : args){
            if(index != 0) {
                objectList.add(string);
            }
            index++;
        }
        return objectList.toArray(new String[]{});
    }
}
