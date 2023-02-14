package me.will0mane.plugins.adventure.systems.executors.hash;

import me.will0mane.plugins.adventure.systems.executors.Executor;

import java.util.function.Function;

public class HashExecutor<T,R> extends Executor<T,R> {

    private final Function<T, R> function;

    public HashExecutor(Function<T,R> function){
        this.function = function;
    }

    @Override
    protected R workerTask(T data) {
        return function.apply(data);
    }
}
