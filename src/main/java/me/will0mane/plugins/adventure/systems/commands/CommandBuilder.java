package me.will0mane.plugins.adventure.systems.commands;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public abstract class CommandBuilder implements CommandExecutor{

    protected CommandSender commandSender;
    protected Command command;
    protected String label;
    protected String[] args;

    protected CommandBuilder(String id){
        Objects.requireNonNull(Adventure.getInstance().getCommand(id)).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] strings) {
        this.commandSender = commandSender;
        this.command = command;
        this.label = label;
        this.args = strings;
        try {
            CommandResponse response = trigger(commandSender,command,label,strings);
            response.getOnCall().accept(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws Exception;

    public Command getCommand() {
        return command;
    }

    public CommandSender getCommandSender() {
        return commandSender;
    }

    public String getLabel() {
        return label;
    }

    public String[] getArgs() {
        return args;
    }

    public void sendMessageToSender(String... messages){
        if(commandSender == null) return;
        for(String m : messages){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
        }
    }
}
