package me.will0mane.plugins.adventure.game.commands;

import me.will0mane.plugins.adventure.Adventure;
import me.will0mane.plugins.adventure.systems.chat.ChatUtils;
import me.will0mane.plugins.adventure.systems.commands.CommandBuilder;
import me.will0mane.plugins.adventure.systems.commands.response.CommandResponse;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import me.will0mane.plugins.adventure.systems.workers.tasks.WorkerTask;
import me.will0mane.plugins.adventure.systems.workers.types.WorkerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class LoopCommand extends CommandBuilder {

    private static final Worker<WorkerType> worker = new Worker<>(Adventure.getInstance());

    public LoopCommand() {
        super("loop");
    }

    @Override
    public CommandResponse trigger(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!(sender instanceof Player player)) return CommandResponse.ONLY_PLAYERS;
        if(!(sender.hasPermission("adventure.loop"))) return CommandResponse.NO_PERMISSION;
        int times = Integer.parseInt(args[0]);
        long delay = Long.parseLong(args[1]);
        StringBuilder sb = new StringBuilder();
        for(int i = 2; i < args.length; i++) {
            if (i > 2) sb.append(" ");
            sb.append(args[i]);
        }
        String cmd = sb.toString();
        ChatUtils.sendMessageTranslated(player, "&cRunning: " + cmd);
        worker.timer(new WorkerTask(worker) {
            int timeDone = 0;
            @Override
            public void run() {
                if(timeDone >= times){
                    cancel();
                    return;
                }
                player.performCommand(cmd);
                timeDone++;
            }
        }, 0, delay);
        return CommandResponse.HANDLED;
    }
}
