package me.will0mane.plugins.adventure.systems.workers;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.workers.tasks.WorkerTask;
import me.will0mane.plugins.adventure.systems.workers.types.WorkerType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Worker<T extends WorkerType> {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Getter
    private final Map<Integer, WorkerTask> taskMap = new HashMap<>();
    private final Plugin plugin;

    public Worker(Plugin plugin){
        this.plugin = plugin;
    }

    public void later(Consumer<Worker<?>> consumer, long delay){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTaskLater(plugin, delay);
    }

    public void laterAsync(Consumer<Worker<?>> consumer, long delay){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTaskLaterAsynchronously(plugin, delay);
    }

    public void timer(Consumer<Worker<?>> consumer, long after, long delay){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTaskTimer(plugin, after, delay);
    }

    public void timerAsync(Consumer<Worker<?>> consumer, long after, long delay){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTaskTimerAsynchronously(plugin, after, delay);
    }

    public void now(Consumer<Worker<?>> consumer){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTask(plugin);
    }

    public void nowAsync(Consumer<Worker<?>> consumer){
        int taskID = getRandomID();
        WorkerTask task = new WorkerTask(this, consumer, taskID);
        addTask(task, taskID);
        task.runTaskAsynchronously(plugin);
    }

    private int getRandomID(){
        return random.nextInt(0,9999);
    }

    private void addTask(WorkerTask task){
        taskMap.put(getRandomID(), task);
    }

    private void addTask(WorkerTask task, int taskID){
        taskMap.put(taskID, task);
    }

    public void cancelTask(int taskID) {
        WorkerTask workerTask = taskMap.getOrDefault(taskID, null);
        if(workerTask == null) return;

        workerTask.cancel();
        taskMap.remove(taskID);
    }
}
