package id.levalapp.grask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Grask {
    private final List<List<Runnable>> tasks = new ArrayList<>();

    public Grask proc(Runnable... runnables) {
        tasks.add(List.of(runnables));
        return this;
    }

    public void grusk() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            tasks.forEach(taskGroup -> taskGroup.forEach(runnable -> {
                try {
                    executor.submit(runnable).get();
                } catch (Exception e) {
                    throw new RuntimeException("Task execution failed", e);
                }
            }));
        }
    }
}
