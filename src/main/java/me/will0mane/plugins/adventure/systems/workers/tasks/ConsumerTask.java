package me.will0mane.plugins.adventure.systems.workers.tasks;

import me.will0mane.plugins.adventure.systems.workers.Worker;

import java.util.function.Consumer;

public class ConsumerTask extends WorkerTask{

    private final Consumer<Worker<?>> consumer;

    public ConsumerTask(Worker<?> worker, Consumer<Worker<?>> consumer){
        super(worker);
        this.consumer = consumer;
    }

    public ConsumerTask(Worker<?> worker, Consumer<Worker<?>> consumer, int taskID){
        super(worker, taskID);
        this.consumer = consumer;
    }

    @Override
    public void run() {
        if(consumer == null) return;

        consumer.accept(super.getWorker());
    }
}
