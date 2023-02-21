package me.will0mane.plugins.adventure.systems.commands.register;

import me.will0mane.plugins.adventure.game.commands.*;
import me.will0mane.plugins.adventure.game.commands.pet_menu.PetMenuCommand;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegister {

    private static Map<String, CommandBuilder> builderMap = new HashMap<>();

    public static void registerCommand(String id, CommandBuilder builder){
        builderMap.put(id, builder);
    }

    public static Optional<CommandBuilder> getCommand(String  id){
        if(!builderMap.containsKey(id)) return Optional.empty();
        return Optional.of(builderMap.get(id));
    }

    public static void unregisterCommand(String id){
        Optional<CommandBuilder> builder = getCommand(id);
        if(builder.isEmpty()) return;
        builderMap.remove(id);
    }

    public void registerDefaults() {
        registerCommand("giveitem", new GiveItemCommand());
        registerCommand("clearinv", new ClearInventoryCommand());
        registerCommand("shblock", new SpawnHackingBlock());
        registerCommand("spawnnpc", new SpawnNPCCommand());
        registerCommand("debug", new DebugCommand());
        registerCommand("stats", new StatsCommand());
        registerCommand("setitemstate", new SetItemStateCommand());
        registerCommand("statistics", new StatisticCommand());
        registerCommand("createpet", new CreatePetCommand());
        registerCommand("pets", new PetMenuCommand());
    }
}
