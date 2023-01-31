package me.will0mane.plugins.adventure.systems.commands.response;

import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;

import java.util.function.Consumer;

public class CommandResponse {

    public static final CommandResponse HANDLED = new CommandResponse((commandBuilder) -> {});
    public static final CommandResponse ONLY_PLAYERS = new CommandResponse((commandBuilder -> {commandBuilder.sendMessageToSender("&cOnly players can run this command!");}));
    public static final CommandResponse ONLY_CONSOLE = new CommandResponse((commandBuilder -> {commandBuilder.sendMessageToSender("&cOnly the console can run this command!");}));
    public static final CommandResponse NO_PERMISSION = new CommandResponse((commandBuilder -> {commandBuilder.sendMessageToSender("&cYou don't have the permission to run this command!");}));

    private final Consumer<CommandBuilder> onCall;

    public CommandResponse(Consumer<CommandBuilder> onCall){
        this.onCall = onCall;
    }

    public Consumer<CommandBuilder> getOnCall() {
        return onCall;
    }
}
