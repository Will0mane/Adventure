package me.will0mane.plugins.adventure.systems.workers;

import lombok.Getter;
import me.will0mane.plugins.adventure.systems.workers.tasks.ConsumerTask;
import me.will0mane.plugins.adventure.systems.workers.tasks.WorkerTask;
import me.will0mane.plugins.adventure.systems.workers.types.WorkerType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Worker<T extends WorkerType> {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Getter
    private final Map<Integer, WorkerTask> taskMap = new HashMap<>();
    @Getter
    private final Plugin plugin;

    public Worker(Plugin plugin){
        this.plugin = plugin;
    }

    public int toHash(T data){
        return data.hashCode();
    }

    public WorkerTask later(Consumer<Worker<?>> consumer, long delay){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskLater(getPlugin(), delay);
        return task;
    }

    public WorkerTask later(WorkerTask task, long delay){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskLater(getPlugin(), delay);
        return task;
    }

    public WorkerTask laterAsync(Consumer<Worker<?>> consumer, long delay){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskLaterAsynchronously(plugin, delay);
        return task;
    }

    public WorkerTask laterAsync(WorkerTask task, long delay){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskLaterAsynchronously(getPlugin(), delay);
        return task;
    }

    public WorkerTask timer(Consumer<Worker<?>> consumer, long after, long delay){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskTimer(plugin, after, delay);
        return task;
    }

    public WorkerTask timer(WorkerTask task, long after, long delay){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskTimer(getPlugin(), after, delay);
        return task;
    }

    public WorkerTask timerAsync(Consumer<Worker<?>> consumer, long after, long delay){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskTimerAsynchronously(plugin, after, delay);
        return task;
    }

    public WorkerTask timerAsync(WorkerTask task, long after, long delay){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskTimerAsynchronously(getPlugin(), after, delay);
        return task;
    }

    public WorkerTask now(Consumer<Worker<?>> consumer){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTask(plugin);
        return task;
    }

    public WorkerTask now(WorkerTask task){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTask(getPlugin());
        return task;
    }

    public WorkerTask nowAsync(Consumer<Worker<?>> consumer){
        int taskID = getRandomID();
        WorkerTask task = new ConsumerTask(this, consumer, taskID);
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskAsynchronously(plugin);
        return task;
    }

    public WorkerTask nowAsync(WorkerTask task){
        int taskID = getRandomID();
        task.setUsedID(taskID);
        addTask(task, taskID);
        task.runTaskAsynchronously(getPlugin());
        return task;
    }

    private int getRandomID(){
        return random.nextInt(0,999999);
    }

    @SuppressWarnings("unused")
    public void addTask(WorkerTask task){
        addTask(task, getRandomID());
    }

    public void addTask(WorkerTask task, int taskID){
        taskMap.put(taskID, task);
    }

    /**
     * Not suggested! This method uses the task id to remove it, this
     * can cancel other tasks if they have that id and not the desired one
     * as when you register a task a new id is generated and the WorkerTask.id isn't used.
     * @since Always
     */
    @SuppressWarnings("unused")
    public void removeTask(WorkerTask task){
        removeTask(task.getTaskId());
    }

    public void removeTask(int taskID){
        getTask(taskID).ifPresent(WorkerTask::cancel);
        taskMap.remove(taskID);
    }

    private Optional<WorkerTask> getTask(int taskID) {
        if(!taskMap.containsKey(taskID)) return Optional.empty();
        return Optional.of(taskMap.get(taskID));
    }

    public void cancelTask(int taskID) {
        WorkerTask workerTask = taskMap.getOrDefault(taskID, null);
        if(workerTask == null) return;

        workerTask.cancel();
        taskMap.remove(taskID);
    }
}
