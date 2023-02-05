package me.will0mane.plugins.adventure.systems.workers.tasks;

import me.will0mane.plugins.adventure.systems.workers.Worker;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class WorkerTask extends BukkitRunnable {

    private final Worker<?> worker;
    private final Consumer<Worker<?>> workerConsumer;
    private final int taskID;

    public WorkerTask(Worker<?> worker, Consumer<Worker<?>> runConsumer){
        this.worker = worker;
        this.workerConsumer = runConsumer;
        this.taskID = -1;
    }

    public WorkerTask(Worker<?> worker, Consumer<Worker<?>> runConsumer, int taskID){
        this.worker = worker;
        this.workerConsumer = runConsumer;
        this.taskID = taskID;
    }

    @Override
    public void run() {
        workerConsumer.accept(worker);
    }

    @Override
    public synchronized void cancel(){
        worker.cancelTask(taskID);
    }
}
