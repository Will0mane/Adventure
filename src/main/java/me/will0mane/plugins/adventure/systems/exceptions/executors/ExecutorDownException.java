package me.will0mane.plugins.adventure.systems.exceptions.executors;

import java.util.UUID;

/**
 * Used to denote an exception where a task is being inputted into an Executor while it is down
 */
public class ExecutorDownException extends ExecutorException{

    /**
     * Constructor for passing in the Executor's ID
     * @param executorID The executor id
     */
    public ExecutorDownException(UUID executorID) {
        this("Cannot handle Executor task because the executor is down! Please start it before using it (Executor.start())" +
                " (" + executorID.toString() + ")");
    }

    /**
     * Constructor for passing in a specific message
     * @param message The message
     */
    public ExecutorDownException(String message){
        super(message);
    }
}
