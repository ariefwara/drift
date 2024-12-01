package id.levalapp.drift;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The Drift class provides a framework for executing tasks in parallel
 * and allows chaining of operations with fallback and side effects.
 */
public class Drift {
    private final Map<String, Object> state;
    private boolean isEnded = false;
    private Object[] finalResult;

    /**
     * Constructs a new Drift instance with the specified initial state.
     *
     * @param initialState the initial state to be used by the tasks
     */
    public Drift(Map<String, Object> initialState) {
        this.state = initialState;
    }

    /**
     * Adds a group of tasks to be executed in parallel. Each task operates
     * on the same Drift instance and can modify its state or end the process.
     *
     * @param tasks the tasks to be executed in parallel
     * @return the current Drift instance for method chaining
     */
    @SafeVarargs
    public final Drift shift(Function<Drift, Drift>... tasks) {
        if (isEnded) return this; // Skip further processing if already ended

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<Future<Drift>> futures = new ArrayList<>();

        try {
            for (Function<Drift, Drift> task : tasks) {
                futures.add(executor.submit(() -> task.apply(this)));
            }

            // Wait for all tasks to complete
            for (Future<Drift> future : futures) {
                future.get(); // Ensure all tasks execute
            }
        } catch (Exception e) {
            throw new RuntimeException("Task execution failed", e);
        } finally {
            executor.shutdown();
        }

        return this;
    }

    /**
     * Executes a fallback operation if no tasks have ended the process.
     *
     * @param fallback the fallback operation to execute if no task ends the process
     * @return the current Drift instance for method chaining
     */
    public Drift grip(Function<Drift, Drift> fallback) {
        if (!isEnded) {
            fallback.apply(this);
        }
        return this;
    }

    /**
     * Ends the process with a specific result.
     *
     * @param result the result to end the process with, typically including a status code and message
     * @return the current Drift instance
     */
    public Drift end(Object... result) {
        if (!isEnded) {
            isEnded = true;
            finalResult = result;
        }
        return this;
    }

    /**
     * Passes additional state information without ending the process.
     *
     * @param additionalState the additional state to be passed
     * @return the current Drift instance
     */
    public Drift pass(Map<String, Object> additionalState) {
        if (!isEnded) {
            state.putAll(additionalState);
        }
        return this;
    }

    /**
     * Performs a trailing side effect asynchronously.
     *
     * @param asyncTask the asynchronous task to execute as a side effect
     */
    public Drift trail(Consumer<Drift> asyncTask) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> asyncTask.accept(this));
        executor.shutdown();
        return this;
    }

    /**
     * Gets the final result of the process.
     *
     * @return the final result as an Object array, or a default result if no resolution was reached
     */
    public Object[] getFinalResult() {
        return finalResult != null ? finalResult : new Object[] {500, "No Resolution"};
    }

    /**
     * Gets the current state of the Drift instance.
     *
     * @return the current state as a Map
     */
    public Map<String, Object> getState() {
        return state;
    }

    /**
     * Gets the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if the key does not exist
     */
    public Object get(String key) {
        return state.get(key);
    }

    /**
     * Gets the value associated with the specified key, cast to the specified class.
     *
     * @param key the key whose associated value is to be returned
     * @param clazz the class to cast the value to
     * @param <T> the type of the class
     * @return the value associated with the specified key cast to the specified class, or null if the key does not exist
     * @throws ClassCastException if the value cannot be cast to the specified class
     */
    public <T> T get(String key, Class<T> clazz) {
        Object value = state.get(key);
        if (value != null) {
            return clazz.cast(value);
        }
        return null;
    }
}
