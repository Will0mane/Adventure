package me.will0mane.plugins.adventure.systems.executors;

import lombok.SneakyThrows;
import me.will0mane.plugins.adventure.systems.exceptions.executors.ExecutorDownException;

import java.util.UUID;

public abstract class Executor<T,R> {

    private boolean executorDown = false;
    private final UUID executorID = UUID.randomUUID();

    public void executorDown(){
        executorDown = true;
    }

    public void start(){
        executorDown = false;
    }

    @SneakyThrows
    public R process(T data){
        if(executorDown) {
            throw new ExecutorDownException(executorID);
        }
        return workerTask(data);
    }

    protected abstract R workerTask(T data);


}
