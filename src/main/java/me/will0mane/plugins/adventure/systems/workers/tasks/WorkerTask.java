package me.will0mane.plugins.adventure.systems.workers.tasks;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.workers.Worker;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class WorkerTask extends BukkitRunnable {

    @Getter
    private final Worker<?> worker;
    private final int taskID;

    protected WorkerTask(Worker<?> worker){
        this.worker = worker;
        this.taskID = -1;
    }

    protected WorkerTask(Worker<?> worker, int taskID){
        this.worker = worker;
        this.taskID = taskID;
    }

    @Override
    public synchronized void cancel(){
        worker.cancelTask(taskID);
    }
}
