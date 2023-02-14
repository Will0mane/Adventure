package me.will0mane.plugins.adventure.systems.executors.hash;

import me.will0mane.plugins.adventure.systems.executors.BiExecutor;

import java.util.function.BiFunction;

public class HashBiExecutor<T,U,R> extends BiExecutor<T,U,R> {

    private final BiFunction<T,U,R> biFunction;

    public HashBiExecutor(BiFunction<T,U,R> biFunction){
        this.biFunction = biFunction;
    }

    @Override
    protected R workerTask(T data1, U data2) {
        return biFunction.apply(data1,data2);
    }
}
