package me.will0mane.plugins.adventure.systems.workers.tasks;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class WorkerTask extends BukkitRunnable {

    @Getter
    private final Worker<?> worker;
    @Getter
    private int usedTaskID;

    protected WorkerTask(Worker<?> worker){
        this.worker = worker;
        this.usedTaskID = -1;
    }

    protected WorkerTask(Worker<?> worker, int taskID){
        this.worker = worker;
        this.usedTaskID = taskID;
    }

    public void setUsedID(int taskID){
        this.usedTaskID = taskID;
    }
}
