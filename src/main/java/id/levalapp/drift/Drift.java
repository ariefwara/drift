package id.levalapp.drift;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class Drift {
    private final List<Object> initialParams;
    private final List<List<Function<List<Object>, Object>>> taskGroups = new ArrayList<>();

    public Drift(Object... initialParams) {
        this.initialParams = List.of(initialParams);
    }

    @SafeVarargs
    public final <R> Drift shift(MultiParamFunction<R>... tasks) {
        // Convert tasks into functions that operate on `List<Object>`
        List<Function<List<Object>, Object>> taskList = new ArrayList<>();
        for (MultiParamFunction<R> task : tasks) {
            taskList.add(inputs -> task.apply(inputs.toArray()));
        }
        taskGroups.add(taskList);
        return this;
    }

    public <R> R swift(MultiParamFunction<R> finalTask) {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        List<Object> currentResults = new ArrayList<>(initialParams);

        try {
            for (List<Function<List<Object>, Object>> taskGroup : taskGroups) {
                currentResults = executeTasks(executor, taskGroup, currentResults);
            }
            return finalTask.apply(currentResults.toArray());
        } finally {
            executor.shutdown();
        }
    }

    private List<Object> executeTasks(ExecutorService executor, List<Function<List<Object>, Object>> tasks, List<Object> inputs) {
        List<Future<Object>> futures = new ArrayList<>();
        for (Function<List<Object>, Object> task : tasks) {
            futures.add(executor.submit(() -> task.apply(inputs)));
        }

        List<Object> results = new ArrayList<>();
        for (Future<Object> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                throw new RuntimeException("Task execution failed", e);
            }
        }
        return results;
    }

    @FunctionalInterface
    public interface MultiParamFunction<R> {
        R apply(Object... params);
    }
}
